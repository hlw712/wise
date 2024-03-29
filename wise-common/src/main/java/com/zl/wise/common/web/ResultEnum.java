package com.zl.wise.common.web;

import lombok.Getter;

@Getter
public enum ResultEnum implements IResult {

    SUCCESS("2000", "接口调用成功"),
    VALIDATE_FAILED("2002", "参数校验失败"),
    COMMON_FAILED("2003", "接口调用失败"),
    FORBIDDEN("2004", "没有权限访问资源");

    private String code;
    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
