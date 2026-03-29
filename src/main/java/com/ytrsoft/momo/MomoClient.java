package com.ytrsoft.momo;

import com.ytrsoft.momo.core.ApiAccess;
import com.ytrsoft.momo.core.Crypto;
import com.ytrsoft.momo.core.KeyExchange;
import com.ytrsoft.momo.model.LoginResult;
import javax.annotation.Nullable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the Momo SDK.
 *
 * <p>Provides login, logout, and custom API access. Thread-safe after construction.
 *
 * <pre>{@code
 * MomoConfig config = new MomoConfig.Builder()
 *     .account("your_account")
 *     .password("your_password")
 *     .build();
 *
 * MomoClient client = new MomoClient(config);
 * LoginResult result = client.login();
 *
 * // Custom API call
 * ApiAccess access = client.createApiAccess("/v2/nearby/people/lists");
 * access.param("lat", "39.9");
 * JSONObject data = access.doRequest();
 *
 * client.logout();
 * }</pre>
 */
public final class MomoClient {

  private static final Logger LOG = LoggerFactory.getLogger(MomoClient.class);

  private final MomoConfig config;
  private final String key;
  private final String kv;
  private final String ck;

  @Nullable private volatile String session;

  /**
   * Creates a new client and performs the ECDH key exchange immediately.
   *
   * @param config the SDK configuration
   * @throws MomoException if the key exchange fails
   */
  public MomoClient(MomoConfig config) {
    this.config = config;

    KeyExchange.ExchangeResult exchange = KeyExchange.execute();
    this.key = exchange.getKey();
    this.kv = exchange.getKv();
    this.ck = exchange.getCk();
  }

  /**
   * Logs in with the credentials from the configuration.
   *
   * @return a {@link LoginResult} containing the session and key info
   * @throws MomoException if login fails or the response is invalid
   */
  public LoginResult login() {
    JSONObject params = new JSONObject()
        .put("account", config.getAccount())
        .put("password", Crypto.md5(config.getPassword()))
        .put("etype", MomoConfig.LOGIN_ETYPE)
        .put("apksign", config.getApkSign())
        .put("uid", config.getId());

    JSONObject body = new JSONObject()
        .put("ck", ck)
        .put("X-KV", kv)
        .put("map_id", generateMapId())
        .put("code_version", MomoConfig.CODE_VERSION);

    ApiAccess access = newAccess(MomoConfig.PATH_LOGIN, null);
    access.params(params).body(body);

    JSONObject response = access.doLogin();
    LOG.debug("Login response: {}", response);

    String sessionId = extractSession(response);
    this.session = sessionId;
    LOG.info("Login succeeded, session acquired");

    return new LoginResult(sessionId, key, kv, ck);
  }

  /**
   * Logs out the current session.
   *
   * @return the logout token value, or null if not present in the response
   * @throws MomoException if there is no active session
   */
  @Nullable
  public String logout() {
    if (session == null || session.isEmpty()) {
      throw new MomoException("No active session to logout");
    }

    JSONObject params = new JSONObject()
        .put("source", MomoConfig.LOGOUT_SOURCE);

    ApiAccess access = newAccess(MomoConfig.PATH_LOGOUT, session);
    access.params(params);

    JSONObject response = access.doRequest();
    LOG.debug("Logout response: {}", response);

    String token = extractLogoutToken(response);
    this.session = null;
    LOG.info("Logout succeeded");

    return token;
  }

  /**
   * Creates an {@link ApiAccess} for calling an arbitrary API endpoint using the
   * established key exchange and current session.
   *
   * @param path the API path (e.g., {@code "/v2/nearby/people/lists"})
   * @return a new, single-use {@link ApiAccess}
   */
  public ApiAccess createApiAccess(String path) {
    return newAccess(path, session);
  }

  /** Returns the current session ID, or null if not logged in. */
  @Nullable
  public String getSession() { return session; }

  /** Returns whether the client has an active session. */
  public boolean isLoggedIn() { return session != null && !session.isEmpty(); }

  public String getKey() { return key; }

  public String getKv() { return kv; }

  public String getCk() { return ck; }

  public MomoConfig getConfig() { return config; }

  // ---------------------------------------------------------------------------
  // Internal
  // ---------------------------------------------------------------------------

  private ApiAccess newAccess(String path, @Nullable String currentSession) {
    String url = buildUrl(path);
    return new ApiAccess(url, currentSession, kv, config.getUserAgent(), key,
        config.getHttpClient(), config.getInterceptors());
  }

  private String buildUrl(String path) {
    StringBuilder sb = new StringBuilder(MomoConfig.BASE_URL).append(path);
    String account = config.getAccount();
    if (account != null && !account.isEmpty()) {
      sb.append("?fr=").append(account);
    }
    return sb.toString();
  }

  private static String extractSession(JSONObject response) {
    JSONObject data = response.optJSONObject("data");
    if (data == null) {
      int errCode = response.optInt("errcode", -1);
      String errMsg = response.optString("errmsg", "Unknown error");
      throw new MomoException("Login failed: " + errMsg, errCode);
    }
    String sessionId = data.optString("session", null);
    if (sessionId == null || sessionId.isEmpty()) {
      throw new MomoException("Login response missing session field");
    }
    return sessionId;
  }

  @Nullable
  private static String extractLogoutToken(JSONObject response) {
    JSONObject tokenObj = response.optJSONObject("l_token");
    return tokenObj != null ? tokenObj.optString("value", null) : null;
  }

  private static String generateMapId() {
    long millis = System.currentTimeMillis() % 1_000_000L;
    if (millis < 100_000L) {
      millis += 100_000L;
    }
    int random = (int) (Math.random() * 9000) + 1000;
    return String.valueOf(millis) + random;
  }
}
