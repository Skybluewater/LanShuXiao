package com.bitmotel.lanshuxiao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
public enum ResponseCode {
    OK(200, "Successive"),
    PERMISSION_DENIED(403, "Permission denied"),
    RESOURCE_NOT_FOUND(404, "Resource not found");

    public Integer code;
    public String msg;

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
