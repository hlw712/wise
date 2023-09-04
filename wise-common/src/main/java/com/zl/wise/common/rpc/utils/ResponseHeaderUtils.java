package com.zl.wise.common.rpc.utils;

import java.util.function.Function;

public class ResponseHeaderUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHeaderUtils.class);

    private ResponseHeaderUtils() {
    }

    public static ResponseHeader exceptionHeader(Throwable e, boolean enableTrace, Function<Throwable, ResponseHeaderBuilder> customizedExceptionHeader) {
        ResponseHeaderBuilder responseHeaderBuilder = null;
        if (customizedExceptionHeader != null) {
            responseHeaderBuilder = (ResponseHeaderBuilder)customizedExceptionHeader.apply(e);
        }

        if (responseHeaderBuilder == null) {
            if (e instanceof I18nErrorCodeException) {
                responseHeaderBuilder = i18nErrorCodeExceptionResponseHeaderBuilder((I18nErrorCodeException)e);
            } else if (e instanceof ErrorCodeException) {
                responseHeaderBuilder = errorCodeExceptionResponseHeaderBuilder((ErrorCodeException)e);
            } else {
                responseHeaderBuilder = normalExceptionResponseHeaderBuilder(e);
            }
        }

        if (enableTrace) {
            responseHeaderBuilder = responseHeaderBuilder.trace(ExceptionUtils.getStackTrace(e));
        }

        return responseHeaderBuilder.build();
    }

    private static ResponseHeaderBuilder i18nErrorCodeExceptionResponseHeaderBuilder(I18nErrorCodeException e) {
        logger.trace("code:{},message:{},i18nMessage:{}", new Object[]{e.getErrorCode(), e.getMessage(), e.getI18nMessage()});
        return ResponseHeader.builder().systemCode(SystemCode.SUCCESS).businessCode(e.getErrorCode()).message(e.getMessage()).i18nMessage(e.getI18nMessage());
    }

    private static ResponseHeaderBuilder errorCodeExceptionResponseHeaderBuilder(ErrorCodeException e) {
        logger.trace("code:{},message:{}", e.getErrorCode(), e.getMessage());
        return ResponseHeader.builder().systemCode(SystemCode.SUCCESS).businessCode(e.getErrorCode()).message(e.getMessage());
    }

    private static ResponseHeaderBuilder normalExceptionResponseHeaderBuilder(Throwable e) {
        logger.trace("message:{}", e.getMessage());
        return ResponseHeader.builder().systemCode(SystemCode.FAILURE).message(e.getMessage());
    }
}
