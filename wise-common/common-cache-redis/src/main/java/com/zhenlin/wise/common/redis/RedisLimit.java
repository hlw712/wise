package com.zhenlin.wise.common.redis;

import java.lang.annotation.*;

/**
 *
 *
 * @author huanglinwei
 * @date 2023/9/15 16:46
 * @version PM-3124
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLimit {

    // 资源名称
    String name() default "";

    // 资源key前缀
    String keyPrefix() default "";

    // 时间, 单位是秒
    int period();

    // 最多访问次数
    int count();

    // 类型
    LimitType limitType() default LimitType.CUSTOMER;

    // 提示信息
    String msg() default "系统繁忙,请稍后再试";

}

