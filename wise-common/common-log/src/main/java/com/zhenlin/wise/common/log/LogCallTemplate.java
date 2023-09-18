package com.zhenlin.wise.common.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhou.liu
 * @version : LogCallTemplate.java, v 0.1 2020/4/8 7:12 pm liuzhou Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface LogCallTemplate {
	
	@AliasFor("message")
	String value() default "";
	
	@AliasFor("value")
	String message() default "";
	
}
