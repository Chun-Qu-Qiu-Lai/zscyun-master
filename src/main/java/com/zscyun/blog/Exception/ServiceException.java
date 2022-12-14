package com.zscyun.blog.Exception;

/**
 * @author 蛋炒饭不加蛋
 *
 * 异常处理
 */
public class ServiceException extends RuntimeException {
  public ServiceException() {
    super();
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }

  protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
