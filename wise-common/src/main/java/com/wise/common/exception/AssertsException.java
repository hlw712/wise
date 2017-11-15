/** Created by flym at 12-12-12 */
package com.wise.common.exception;

/**
 * 业务断言异常,表示不能满足业务上的要求，那么就中断，直接返回信息到页面
 *
 */
public class AssertsException extends RuntimeException {
	/** 详细的业务要求错误信息 */
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public AssertsException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public AssertsException(String errorMessage, String message) {
		super(message);
		this.errorMessage = errorMessage;
	}

	public AssertsException(String errorMessage, String message, Throwable cause) {
		super(message, cause);
		this.errorMessage = errorMessage;
	}

	public AssertsException(String errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}
}
