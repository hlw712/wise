package com.wise.common.exception;

/**
 * Created by hlw on 2017/11/15.
 */
public class MsgException extends BaseException {
    protected MsgException(Long messageCode) {
        super(messageCode);
    }
}
