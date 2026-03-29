package com.ytrsoft.momo.model;

/**
 * Immutable result of a successful login operation.
 */
public final class LoginResult {

  private final String session;
  private final String key;
  private final String kv;
  private final String ck;

  public LoginResult(String session, String key, String kv, String ck) {
    this.session = session;
    this.key = key;
    this.kv = kv;
    this.ck = ck;
  }

  /** Returns the session ID used for authenticated requests. */
  public String getSession() { return session; }

  /** Returns the Base64-encoded shared secret key. */
  public String getKey() { return key; }

  /** Returns the key version string. */
  public String getKv() { return kv; }

  /** Returns the Base64-encoded client key. */
  public String getCk() { return ck; }

  @Override
  public String toString() {
    return "LoginResult{session='" + session + "', kv='" + kv + "'}";
  }
}
