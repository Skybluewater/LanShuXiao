package com.bitmotel.lanshuxiao.response;

import com.bitmotel.lanshuxiao.response.ResponseCode;
import lombok.Data;

@Data
public class Response<T> {
    //状态码
    private Integer code;

    //信息
    private String msg;

    //返回类型，泛型
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseCode.OK.code, ResponseCode.OK.msg, data);
    }

    public static <T> Response<T> success(String msg, T data) {
        return new Response<T>(200, msg, data);
    }

    public static Response<Object> fail(Integer code, String msg) {
        return new Response<Object>(code, msg, null);
    }

    public static <T> Response<T> fail(Integer code, String msg, T data) {
        return new Response<T>(code, msg, data);
    }

    public static <T> Response<T> fail(String msg, T data) {
        return new Response<T>(400, msg, data);
    }

    public static Response<Object> fail(String msg) {
        return new Response<>(400, msg, null);
    }

    public Response() {
    }

    public Response(Integer code, String msg, T data) {
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