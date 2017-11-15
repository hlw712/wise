package com.wise.common.exception;

/**
 * Created by hlw on 2017/11/15.
 */
public class BizException extends BaseException {
    protected BizException(Long messageCode) {
        super(messageCode);
    }
}
