package com.bitmotel.lanshuxiao.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusCode {
    OK(200),
    PERMISSION_DENIED(403),
    NOT_FOUND(404);

    private Integer code;
}
