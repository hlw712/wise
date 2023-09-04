package com.zl.wise.common.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 操作日志处理
 *
 * @author huanglinwei
 * @date 2021/1/11 4:31 PM
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface OperateLog {
	
	@AliasFor("message")
	String value() default "";
	
	@AliasFor("value")
	String message() default "";

	IOperateLog logType();
}
