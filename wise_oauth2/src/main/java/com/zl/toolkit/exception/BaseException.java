package com.zl.toolkit.exception;

/**
 * User: huanglw
 * Date: 13-10-15
 * Time: 上午10:27
 */
public abstract class BaseException extends RuntimeException {

    private Long messageCode;

    protected BaseException(Long messageCode) {
        this.messageCode = messageCode;
    }

    protected BaseException(Long messageCode, String message) {
        super(message);
        this.messageCode = messageCode;
    }

    protected BaseException(Long messageCode, String message, Throwable cause) {
        super(message, cause);
        this.messageCode = messageCode;
    }

    protected BaseException(Long messageCode, Throwable cause) {
        super(cause);
        this.messageCode = messageCode;
    }

    public Long getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Long messageCode) {
        this.messageCode = messageCode;
    }
}
