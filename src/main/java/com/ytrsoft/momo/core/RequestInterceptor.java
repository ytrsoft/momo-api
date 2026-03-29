package com.ytrsoft.momo.core;

import java.util.Map;

/**
 * Intercepts API requests before they are sent.
 *
 * <p>Use this to add custom headers, logging, metrics, or modify request parameters
 * without subclassing core classes.
 *
 * <pre>{@code
 * MomoConfig config = new MomoConfig.Builder()
 *     .account("your_account")
 *     .password("your_password")
 *     .addInterceptor((url, headers, params) -> {
 *       headers.put("X-Custom-Header", "value");
 *     })
 *     .build();
 * }</pre>
 */
public interface RequestInterceptor {

  /**
   * Called before each request is sent. Implementations may modify the headers and parameters
   * in place.
   *
   * @param url the request URL (read-only)
   * @param headers the mutable headers map
   * @param formParams the mutable form parameters map
   */
  void intercept(String url, Map<String, String> headers, Map<String, String> formParams);
}
