package com.ytrsoft.momo.core;

import java.util.Map;

/**
 * Abstraction for HTTP transport.
 *
 * <p>Implement this interface to replace the default OkHttp-based transport with a custom
 * implementation (e.g., for testing, Android-specific networking, or alternative HTTP stacks).
 *
 * @see OkHttpClientImpl
 */
public interface HttpClient {

  /**
   * Sends a POST request with form-encoded body parameters.
   *
   * @param url the full request URL
   * @param headers the request headers (never null)
   * @param formParams the form body parameters (never null)
   * @return the response body as a byte array; empty array if the request fails
   */
  byte[] post(String url, Map<String, String> headers, Map<String, String> formParams);
}
