package com.zhenlin.wise.common.core.exception;

/**
 * 服务整合异常（包含：内部服务调用，第三方服务调用 过程中，出现的异常）
 *
 * @author huanglinwei
 * @date 2023/9/4 21:11
 * @version 1.0.0
 */
public class IntegrationException extends BaseException {

    protected IntegrationException(String code) {
        super(code);
    }

    protected IntegrationException(String code, String message) {
        super(code, message);
    }
}
