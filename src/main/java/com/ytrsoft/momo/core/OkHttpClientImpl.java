package com.ytrsoft.momo.core;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default {@link HttpClient} implementation backed by OkHttp.
 *
 * <p>Uses a lazily-initialized shared {@link OkHttpClient} instance with sensible timeouts.
 * The underlying client is safe for concurrent use from multiple threads.
 */
public final class OkHttpClientImpl implements HttpClient {

  private static final Logger LOG = LoggerFactory.getLogger(OkHttpClientImpl.class);

  private static final int CONNECT_TIMEOUT_SECONDS = 10;
  private static final int WRITE_TIMEOUT_SECONDS = 10;
  private static final int READ_TIMEOUT_SECONDS = 30;

  private final OkHttpClient client;

  /** Creates an instance with default timeout settings. */
  public OkHttpClientImpl() {
    this(CONNECT_TIMEOUT_SECONDS, WRITE_TIMEOUT_SECONDS, READ_TIMEOUT_SECONDS);
  }

  /**
   * Creates an instance with custom timeout settings.
   *
   * @param connectTimeoutSeconds connect timeout in seconds
   * @param writeTimeoutSeconds write timeout in seconds
   * @param readTimeoutSeconds read timeout in seconds
   */
  public OkHttpClientImpl(int connectTimeoutSeconds, int writeTimeoutSeconds,
      int readTimeoutSeconds) {
    client = new OkHttpClient.Builder()
        .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
        .build();
  }

  @Override
  public byte[] post(String url, Map<String, String> headers, Map<String, String> formParams) {
    FormBody.Builder formBuilder = new FormBody.Builder();
    for (Map.Entry<String, String> entry : formParams.entrySet()) {
      formBuilder.add(entry.getKey(), entry.getValue());
    }

    Request request = new Request.Builder()
        .url(url)
        .headers(Headers.of(headers))
        .post(formBuilder.build())
        .build();

    try (Response response = client.newCall(request).execute()) {
      ResponseBody body = response.body();
      if (body != null) {
        return body.bytes();
      }
    } catch (IOException e) {
      LOG.error("HTTP POST failed for {}: {}", url, e.getMessage(), e);
    }
    return new byte[0];
  }

  /** Returns a shared global instance with default settings. */
  public static OkHttpClientImpl getDefault() {
    return Holder.INSTANCE;
  }

  private static final class Holder {
    static final OkHttpClientImpl INSTANCE = new OkHttpClientImpl();
  }
}
