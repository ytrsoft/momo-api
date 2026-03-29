package com.ytrsoft.momo;

import com.ytrsoft.momo.core.HttpClient;
import com.ytrsoft.momo.core.OkHttpClientImpl;
import com.ytrsoft.momo.core.RequestInterceptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Immutable configuration for the Momo SDK.
 *
 * <p>Use {@link Builder} to construct instances. All defaults are built-in; only
 * {@code account} and {@code password} are required.
 *
 * <pre>{@code
 * MomoConfig config = new MomoConfig.Builder()
 *     .account("your_account")
 *     .password("your_password")
 *     .build();
 * }</pre>
 */
public final class MomoConfig {

  /** Base URL for all API requests. */
  public static final String BASE_URL = "https://api.immomo.com";
  public static final String PATH_LOGIN = "/api/v2/login";
  public static final String PATH_LOGOUT = "/api/setting/momologout";
  public static final String LOGIN_ETYPE = "2";
  public static final String CODE_VERSION = "2";
  public static final String LOGOUT_SOURCE = "1";

  static final String DEFAULT_ID = "a3931e93ff9cb0bc16e38cf3a14aa599";
  static final String DEFAULT_SIGN = "4f3a531caff3e37c278659cc78bfaecc";
  static final String DEFAULT_USER_AGENT =
      "MomoChat/9.15.4 Android/12625 (V2241A; Android 12; Gapps 0; zh_CN; 1; vivo)";

  private final String id;
  private final String apkSign;
  private final String userAgent;
  private final String account;
  private final String password;
  private final HttpClient httpClient;
  private final List<RequestInterceptor> interceptors;

  private MomoConfig(Builder builder) {
    this.id = builder.id;
    this.apkSign = builder.apkSign;
    this.userAgent = builder.userAgent;
    this.account = builder.account;
    this.password = builder.password;
    this.httpClient = builder.httpClient != null
        ? builder.httpClient : OkHttpClientImpl.getDefault();
    this.interceptors = Collections.unmodifiableList(new ArrayList<>(builder.interceptors));
  }

  public String getId() { return id; }

  public String getApkSign() { return apkSign; }

  public String getUserAgent() { return userAgent; }

  public String getAccount() { return account; }

  public String getPassword() { return password; }

  /** Returns the HTTP client used for all network requests. */
  public HttpClient getHttpClient() { return httpClient; }

  /** Returns an unmodifiable list of request interceptors. */
  public List<RequestInterceptor> getInterceptors() { return interceptors; }

  /**
   * Builder for {@link MomoConfig}.
   */
  public static final class Builder {

    private String id = DEFAULT_ID;
    private String apkSign = DEFAULT_SIGN;
    private String userAgent = DEFAULT_USER_AGENT;
    @Nullable private String account;
    @Nullable private String password;
    @Nullable private HttpClient httpClient;
    private final List<RequestInterceptor> interceptors = new ArrayList<>();

    public Builder() {}

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder apkSign(String apkSign) {
      this.apkSign = apkSign;
      return this;
    }

    public Builder userAgent(String userAgent) {
      this.userAgent = userAgent;
      return this;
    }

    /** Sets the login account (required). */
    public Builder account(String account) {
      this.account = account;
      return this;
    }

    /** Sets the plain-text password (required). It will be MD5-hashed internally. */
    public Builder password(String password) {
      this.password = password;
      return this;
    }

    /**
     * Sets a custom HTTP transport. Defaults to {@link OkHttpClientImpl} if not set.
     */
    public Builder httpClient(HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    /** Adds a {@link RequestInterceptor} to the request pipeline. */
    public Builder addInterceptor(RequestInterceptor interceptor) {
      this.interceptors.add(interceptor);
      return this;
    }

    /**
     * Builds the configuration.
     *
     * @throws IllegalStateException if account or password is not set
     */
    public MomoConfig build() {
      if (account == null || account.isEmpty()) {
        throw new IllegalStateException("Account must not be null or empty");
      }
      if (password == null || password.isEmpty()) {
        throw new IllegalStateException("Password must not be null or empty");
      }
      return new MomoConfig(this);
    }
  }
}
