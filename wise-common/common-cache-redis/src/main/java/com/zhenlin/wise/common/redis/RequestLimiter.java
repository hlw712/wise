package com.zhenlin.wise.common.redis;

import java.lang.annotation.*;

/**
 * 请求限流器注解
 *
 * @author huanglinwei
 * @date 2023/9/15 16:46
 * @version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestLimiter {

    // 资源名称
    String name() default "";

    // 资源key前缀
    String keyPrefix() default "";

    // 时间, 单位是秒
    int period();

    // 最多访问次数
    int count();

    // 类型
    LimiterType limitType() default LimiterType.CUSTOMER;

    // 提示信息
    String msg() default "系统繁忙,请稍后再试";
}

