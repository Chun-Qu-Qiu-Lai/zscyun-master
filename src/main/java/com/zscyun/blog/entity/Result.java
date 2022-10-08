package com.zscyun.blog.entity;


import java.io.Serializable;

/**
 * @author 蛋炒饭不加蛋
 *
 *返回集合
 */
public class Result<T> implements Serializable {

  private int code;
  private String msg;
  private T data;


  public static <T> Result<T> success(ResultStatus resultStatus) {
    return success(resultStatus,null);
  }

  public static <T> Result<T> success(ResultStatus resultStatus,T data) {
    return new Result<T>(resultStatus.getResultCode(), resultStatus.getResultDescription(), data);
  }

  public static <T extends Serializable> Result<T> fail() {
    return fail(null);
  }

  public static <T> Result<T> fail(ResultStatus resultStatus) {
    return new Result<T>(resultStatus.getResultCode(), resultStatus.getResultDescription(),null);
  }

  public Result(int code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
