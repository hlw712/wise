package com.zl.wise.common.log;

import com.zl.wise.common.core.CallBacker;
import com.zl.wise.common.exception.BusinessException;
import com.zl.wise.common.exception.IntegrationException;
import com.zl.wise.common.rpc.exception.I18nExceptionCode;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;

/**
 * <p>执行器模板，记录调用日志</p>
 *
 * @author zhou.liu
 * @version ExecuteTemplate.java v0.1 2018/9/7 上午11:22 liuzhou Exp $
 */
public class ExecuteTemplate {

    /**
     * lambda表达式方式
     * @param callback
     * @param logMessage
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T execute(CallBacker<T> callback, Logger logger, String logMessage,
                                Object... params) {
        StopWatch watch = new StopWatch();
        watch.start();

        T t;
        try {
            logger.info("app ==> remoteServer,msg={},request params={}", logMessage,
                    params);

            t = callback.call();

            logger.info("app <== remoteServer,msg={},response={}", logMessage, t.toString());
        } catch (Exception e) {
            logger.info("app <== remoteServer,msg={},throws Exception", logMessage, e);

            if(e instanceof I18nExceptionCode) {
                throw (I18nExceptionCode) e;
            }
            if(e instanceof BusinessException) {
                throw (BusinessException) e;
            }
            throw new IntegrationException(e.getMessage());
        } finally {
            watch.stop();
            logger.info("app msg={}, call remoteServer end,timeCost={}ms,", logMessage,
                watch.getTotalTimeMillis());
        }
        return t;
    }

}
