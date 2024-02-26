package com.zhenlin.wise.common.redis;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import com.uaepay.merchant.console.frontend.common.enums.ResponseCode;
import com.uaepay.merchant.console.frontend.common.exception.BusinessLogicException;
import com.uaepay.merchant.console.frontend.common.response.BaseResponse;
import com.uaepay.merchant.console.frontend.web.controller.secure.common.SingleValueParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求限流器注解 实现类
 *
 * @author huanglinwei
 * @date 2023/9/15 16:46
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Configuration
public class RequestLimiterAspect implements InitializingBean {

    @Value("${frontend.controller.showTrace}")
    private boolean showTrace;
    private String luaScript;

    @Override
    public void afterPropertiesSet() throws Exception {
        luaScript = loadScriptString("redis_limit.lua");
    }

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(com.uaepay.merchant.console.frontend.web.interceptor.RedisLimit)")
    public Object around(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        RequestLimiter annotation = method.getAnnotation(RequestLimiter.class);
        LimiterType limitType = annotation.limitType();

        String name = annotation.name();
        String key;

        int period = annotation.period();
        int count = annotation.count();

        switch (limitType){
            case IP:
                key = annotation.keyPrefix().concat(httpUtils.getIpAddress());
                break;
            case CUSTOMER:
                key = annotation.keyPrefix().concat(name);
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        ImmutableList<String> keys = ImmutableList.of(key);
        try {
            DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number number = redisTemplate.execute(redisScript, keys, period, count);
            if(number != null && number.intValue() == 1) {
                return pjp.proceed();
            }
            throw new BusinessLogicException(ResponseCode.BUSINESS_EXCEPTION, annotation.msg());

        }catch (Throwable e) {
            if(e instanceof BusinessLogicException) {
                throw new BusinessLogicException(ResponseCode.BUSINESS_EXCEPTION, e.getLocalizedMessage());
            }
            log.error("", e);

            SingleValueParam<String> body = showTrace ? SingleValueParam.<String>builder().value(ExceptionUtils.getStackTrace(e)).build() : null;
            return BaseResponse.failBySystemError(body);
        }
    }

    /**
     * 加载Lua代码
     */
    public String loadScriptString(String fileName) throws IOException {
        Reader reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
        return CharStreams.toString(reader);
    }


}
