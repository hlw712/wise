package com.wise.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
public class SpringContextUtil  implements ApplicationContextAware {

    private static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }

    public static ApplicationContext getContext() {
        if(application==null){
            return null;
        }
        return application;
    }

    public final static<T> T getBean(Class<?> c){
        if(application == null || c == null){
            return null;
        }
        T t=null;
        try {
            t= (T)application.getBean(c);
        } catch (BeansException e) {

        }
        return t;
    }

    public final static<T> T getBean(String beanName, Class<?> requiredType) {
        if(application==null){
            return null;
        }
        return (T)application.getBean(beanName, requiredType);
    }

    public final static<T> T getBean(String beanName) {
        if(application==null){
            return null;
        }
        return (T)application.getBean(beanName);
    }
}
