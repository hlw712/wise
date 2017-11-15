package com.wise.common.exception;

/**
 * Created by hlw on 2017/11/15.
 */
public class BaseException extends RuntimeException {

    private Long code;

    protected BaseException(Long code) {
        this.code = code;
    }

    protected BaseException(Long code, String message) {
        super(message);
        this.code = code;
    }

    protected BaseException(Long code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    protected BaseException(Long code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public Long getcode() {
        return code;
    }

    public void setcode(Long code) {
        this.code = code;
    }
}
