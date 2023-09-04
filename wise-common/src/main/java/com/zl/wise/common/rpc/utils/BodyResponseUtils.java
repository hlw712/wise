package com.zl.wise.common.rpc.utils;

import java.io.Serializable;
import java.util.function.Function;

public class BodyResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(BodyResponseUtils.class);

    public BodyResponseUtils() {
    }

    public static <T extends Serializable> BodyResponse<T> codeBodyResponse(T result, SystemCode systemCode, String businessCode, String message) {
        ResponseHeader responseHeader = ResponseHeader.builder().systemCode(systemCode).businessCode(businessCode).message(message).build();
        return ((BodyResponseBuilder)BodyResponse.bodyBuilder().header(responseHeader)).body(result).build();
    }

    public static <T extends Serializable> BodyResponse<T> successBodyResponse(T result) {
        return codeBodyResponse(result, SystemCode.SUCCESS, BusinessCode.SUCCESS.name(), (String)null);
    }

    public static <T extends Serializable> BodyResponse<T> failureBodyResponse(String message) {
        return codeBodyResponse((Serializable)null, SystemCode.FAILURE, (String)null, message);
    }

    public static <T extends Serializable> BodyResponse<T> exceptionBodyResponse(Throwable throwable) {
        return exceptionBodyResponse(throwable, false, (Function)null);
    }

    public static <T extends Serializable> BodyResponse<T> exceptionBodyResponse(Throwable throwable, boolean enableTrace, Function<Throwable, ResponseHeaderBuilder> customizedExceptionHeader) {
        return ((BodyResponseBuilder)BodyResponse.bodyBuilder().header(ResponseHeaderUtils.exceptionHeader(throwable, enableTrace, customizedExceptionHeader))).build();
    }

    public static <T extends Serializable> T parse(BodyResponse<T> resultResponse, String... silentBusinessCodes) {
        BaseResponseUtils.parse(resultResponse, silentBusinessCodes);
        return resultResponse.getBody();
    }
}
