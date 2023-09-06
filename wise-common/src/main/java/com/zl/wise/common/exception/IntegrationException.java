package com.zl.wise.common.exception;

public class IntegrationException extends BaseException {

    protected IntegrationException(String code) {
        super(code);
    }

    protected IntegrationException(String code, String message) {
        super(code, message);
    }
}
