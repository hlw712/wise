package com.zhenlin.common.validator.exception;

import com.zl.wise.common.exception.BusinessException;

/**
 * 校验异常
 *
 * @author huanglinwei
 * @date 2023/9/4 21:11
 * @version 1.0.0
 */
public class ValidateException extends BusinessException {

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(String code, String message) {
		super(code, message);
	}
}
