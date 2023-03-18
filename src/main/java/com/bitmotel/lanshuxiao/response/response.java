package com.bitmotel.lanshuxiao.response;

import lombok.Data;

@Data
public class response<T> {
    //状态码
    private Integer code;

    //信息
    private String msg;

    //返回类型，泛型
    private T data;

    public static <T> response<T> success(T data) {
        return new response<T>(200, "", data);
    }

    public static <T> response<T> success(String msg, T data) {
        return new response<T>(200, msg, data);
    }

    public static response<Object> fail(Integer code, String msg) {
        return new response<Object>(code, msg, null);
    }

    public static <T> response<T> fail(Integer code, String msg, T data) {
        return new response<T>(code, msg, data);
    }

    public response() {
    }

    public response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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