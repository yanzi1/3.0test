package com.me.data.model;

/**
 * Created by xunice on 09/03/2017.
 */
public class BaseRes<T> {

  private int code;
  private String msg;
  private boolean success;
  private T obj;
  private T body;

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
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

  public T getObj() {
    return obj;
  }

  public void setObj(T obj) {
    this.obj = obj;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
