package com.ytrsoft.momo;

/**
 * Unchecked exception thrown by Momo SDK operations.
 */
public class MomoException extends RuntimeException {

  private static final long serialVersionUID = 2L;

  private final int errorCode;

  public MomoException(String message) {
    this(message, -1, null);
  }

  public MomoException(String message, Throwable cause) {
    this(message, -1, cause);
  }

  public MomoException(String message, int errorCode) {
    this(message, errorCode, null);
  }

  /**
   * Creates an exception with a message, API error code, and optional cause.
   *
   * @param message the detail message
   * @param errorCode the server error code, or {@code -1} if not applicable
   * @param cause the underlying cause (may be null)
   */
  public MomoException(String message, int errorCode, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  /**
   * Returns the server error code, or {@code -1} if this exception did not originate from
   * an API error response.
   */
  public int getErrorCode() {
    return errorCode;
  }
}
