package cn.com.lz.generator.backstage.common.exception;

/**
 * 自定义异常
 *
 */
public class ResultException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private int status;

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public ResultException(String message, int status) {
    super(message);
    this.status = status;
  }

  public ResultException(String message) {
    super(message);
  }


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
