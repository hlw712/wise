package com.zhenlin.wise.common.core.exception;

/**
 * User: huanglw
 * Date: 13-10-15
 * Time: 上午10:27
 */
public class BaseException extends RuntimeException {

    private String code;

    protected BaseException(String code) {
        this.code = code;
    }

    protected BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    protected BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    protected BaseException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
