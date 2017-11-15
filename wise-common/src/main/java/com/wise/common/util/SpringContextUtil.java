package com.wise.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * spring对象动态加载类对象 工具类
 *
 * @author huanglw
 * @date 2017/11/15
 * @updater
 * @version V1.0.0
 *
 * @remarks:
 */
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    public SpringContextUtil() {
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }
}
