package com.zhenlin.wise.common.core;

/**
 * <p>回调执行</p>
 *
 * @author huanglinwei
 * @date 2023/9/4 21:36
 * @version 1.0.0
 */
@FunctionalInterface
public interface CallBacker<T> {
	
	/**
	 * 调用
	 * @return
	 * @throws Exception
	 */
	T call() throws Exception;
}