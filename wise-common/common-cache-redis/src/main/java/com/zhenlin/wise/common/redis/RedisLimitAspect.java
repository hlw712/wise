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
 * @author: zhou.liu
 * @version: RedisLimitAspect.java v0.1
 * @Date: 2022/04/25/6:17 AM liuzhou Exp
 * @Description:
 */
@Slf4j
@Aspect
@Configuration
public class RedisLimitAspect implements InitializingBean {

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
        RedisLimit annotation = method.getAnnotation(RedisLimit.class);
        LimitType limitType = annotation.limitType();

        String name = annotation.name();
        String key;

        int period = annotation.period();
        int count = annotation.count();

        switch (limitType){
            case IP:
                key = annotation.keyPrefix().concat(getIpAddress());
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

    public String getIpAddress(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = request.getHeader("remoteip");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("x-forwarded-for");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        // 如果有多个ip，截取前面ip地址
        if (ip != null && ip.contains(",")) {
            String[] ips = ip.split(",");
            ip = ips[0];
        }
        return ip;
    }

}
