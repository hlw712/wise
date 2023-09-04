package com.zl.wise.common.log;

import com.uaepay.merchant.console.frontend.common.exception.BusinessLogicException;
import com.uaepay.merchant.console.frontend.common.exception.IntegrationException;
import com.zl.wise.common.core.CallBacker;
import io.bitexpress.topia.commons.basic.exception.ErrorCodeException;
import io.bitexpress.topia.commons.basic.exception.i18n.I18nErrorCodeException;
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

            if(e instanceof I18nErrorCodeException) {
                throw (I18nErrorCodeException)e;
            }
            if(e instanceof ErrorCodeException) {
                throw (ErrorCodeException)e;
            }
            if(e instanceof BusinessLogicException) {
                throw (BusinessLogicException)e;
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
