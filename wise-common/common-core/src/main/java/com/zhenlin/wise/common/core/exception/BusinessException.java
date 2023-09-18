package com.zhenlin.wise.common.core.exception;

/**
 * 业务逻辑断言异常,表示不能满足业务上的要求，那么就中断，直接返回信息到页面
 *
 * @author huanglinwei
 * @date 2023/9/4 21:11
 * @version 1.0.0
 */
public class BusinessException extends BaseException {

	protected BusinessException(String code) {
		super(code);
	}

	protected BusinessException(String code, String message) {
		super(code, message);
	}
}
