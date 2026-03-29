package com.ytrsoft.momo.core;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import org.json.JSONObject;

/**
 * Builds and executes a single API request with automatic encryption, signing, and decryption.
 *
 * <p>Each instance is single-use: create one per request via
 * {@link com.ytrsoft.momo.MomoClient#createApiAccess(String)} or the constructor.
 */
public final class ApiAccess {

  private static final byte[] ENCRYPTED_HEADER = {2, 3};

  private final String url;
  @Nullable private final String session;
  private final String kv;
  private final String userAgent;
  private final byte[] key;
  private final HttpClient httpClient;
  private final List<RequestInterceptor> interceptors;
  private final JSONObject params = new JSONObject();
  private final Map<String, String> bodyParams = new LinkedHashMap<>();

  /**
   * Creates a new API access instance.
   *
   * @param url the full request URL
   * @param session the session ID (null for unauthenticated requests such as login)
   * @param kv the key version
   * @param userAgent the User-Agent string
   * @param sharedKey the Base64-encoded shared secret key
   * @param httpClient the HTTP transport implementation
   * @param interceptors request interceptors to apply before sending
   */
  public ApiAccess(String url, @Nullable String session, String kv, String userAgent,
      String sharedKey, HttpClient httpClient, List<RequestInterceptor> interceptors) {
    this.url = url;
    this.session = session;
    this.kv = kv;
    this.userAgent = userAgent;
    this.key = sharedKey.getBytes(StandardCharsets.UTF_8);
    this.httpClient = httpClient;
    this.interceptors = interceptors;
  }

  /** Convenience constructor without interceptors. */
  public ApiAccess(String url, @Nullable String session, String kv, String userAgent,
      String sharedKey, HttpClient httpClient) {
    this(url, session, kv, userAgent, sharedKey, httpClient, Collections.<RequestInterceptor>emptyList());
  }

  // ---------------------------------------------------------------------------
  // Fluent parameter setters
  // ---------------------------------------------------------------------------

  /** Merges all entries from the given JSON into the request parameters. */
  public ApiAccess params(JSONObject extra) {
    for (Map.Entry<String, Object> entry : extra.toMap().entrySet()) {
      params.put(entry.getKey(), entry.getValue());
    }
    return this;
  }

  /** Adds a single request parameter. */
  public ApiAccess param(String name, Object value) {
    params.put(name, value);
    return this;
  }

  /** Merges all entries from the given JSON into the form body fields. */
  public ApiAccess body(JSONObject extra) {
    for (Map.Entry<String, Object> entry : extra.toMap().entrySet()) {
      bodyParams.put(entry.getKey(), String.valueOf(entry.getValue()));
    }
    return this;
  }

  /** Adds a single form body field. */
  public ApiAccess body(String name, String value) {
    bodyParams.put(name, value);
    return this;
  }

  // ---------------------------------------------------------------------------
  // Execution
  // ---------------------------------------------------------------------------

  /**
   * Executes a standard encrypted API request.
   *
   * @return the parsed and decrypted response
   */
  public JSONObject doRequest() {
    byte[] raw = sendRequest();
    byte[] decrypted = Crypto.decode(raw, key);
    return JsonParser.deepParse(BrotliDecoder.decompress(decrypted));
  }

  /**
   * Executes a login request. The response may or may not be encrypted depending on the
   * first two bytes of the server response.
   *
   * @return the parsed response
   */
  public JSONObject doLogin() {
    byte[] raw = sendRequest();
    if (isEncryptedResponse(raw)) {
      byte[] decrypted = Crypto.decode(raw, key);
      return JsonParser.deepParse(BrotliDecoder.decompress(decrypted));
    }
    return JsonParser.deepParse(new String(raw, StandardCharsets.UTF_8));
  }

  // ---------------------------------------------------------------------------
  // Internal
  // ---------------------------------------------------------------------------

  private byte[] sendRequest() {
    byte[] plaintext = params.toString().getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = Crypto.encode(plaintext, key);

    String mzip = Crypto.base64Encode(encrypted);
    bodyParams.put("mzip", mzip);

    byte[] uaBytes = userAgent.getBytes(StandardCharsets.UTF_8);
    byte[] signInput = Crypto.concatArrays(uaBytes, encrypted);
    byte[] signature = Crypto.sign(signInput, key);
    String signValue = Crypto.base64Encode(signature);

    Map<String, String> headers = buildHeaders(signValue);

    for (RequestInterceptor interceptor : interceptors) {
      interceptor.intercept(url, headers, bodyParams);
    }

    return httpClient.post(url, headers, bodyParams);
  }

  private Map<String, String> buildHeaders(String sign) {
    Map<String, String> headers = new LinkedHashMap<>();
    if (session != null && !session.isEmpty()) {
      headers.put("cookie", "SESSIONID=" + session);
    }
    headers.put("X-SIGN", sign);
    headers.put("X-Span-Id", "0");
    headers.put("X-ACT", "br");
    headers.put("X-LV", "1");
    headers.put("X-KV", kv);
    headers.put("Accept-Language", "zh-CN");
    headers.put("X-Trace-Id", UUID.randomUUID().toString().toUpperCase());
    headers.put("User-Agent", userAgent);
    return headers;
  }

  private static boolean isEncryptedResponse(byte[] data) {
    return data.length >= 2
        && data[0] == ENCRYPTED_HEADER[0]
        && data[1] == ENCRYPTED_HEADER[1];
  }
}
