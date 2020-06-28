package cn.com.lz.generator.mysql.plugins.base.common;

import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回数据
 */
@ApiModel("统一返回格式")
public class R<T> implements Serializable {

  private static final long serialVersionUID = -7916551187176186703L;

  private Date timestamp;

  private int status;

  private String msg;

  private T data;


    public R() {
        this.status = 200;
        this.msg="操作成功";
    }

    public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
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

  public R(Date timestamp, int status, String msg) {
    this.timestamp = timestamp;
    this.status = status;
    this.msg = msg;
  }

  public R(Date timestamp, int status, String msg, T data) {
    this.timestamp = timestamp;
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public static R error(Date timestamp, int status, String message) {
    return new R<>(timestamp, status, message);
  }

  public static R error(int status, String message) {
    return new R<>(new Date(), status, message);
  }

  public static R ok(Date timestamp, int status, String message) {
    return new R<>(timestamp, status, message);
  }


  @SuppressWarnings("unchecked")
  public static<T> R ok(String message, T data) {
    return new R(new Date(), HttpStatus.OK.value(), message, data);
  }

  public static R ok(String message) {
    return new R<>(new Date(), HttpStatus.OK.value(), message);
  }

  public static R ok() {
    return new R<>(new Date(), HttpStatus.OK.value(), "操作成功");
  }

  @Override
  public String toString() {
    return "R{" +
            "timestamp=" + timestamp +
            ", status=" + status +
            ", msg='" + msg + '\'' +
            ", data=" + data +
            '}';
  }
}
