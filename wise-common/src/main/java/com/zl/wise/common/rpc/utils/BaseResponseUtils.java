package com.zl.wise.common.rpc.utils;

import com.zl.wise.common.rpc.response.BaseResponse;
import com.zl.wise.common.rpc.response.ResponseHeader;

import java.util.function.Function;

public class BaseResponseUtils {

    public BaseResponseUtils() {
    }

    public static BaseResponse successBaseResponse() {
        return successBaseResponse((String)null);
    }

    public static BaseResponse success(String message) {
        return codeBaseResponse(SystemCode.SUCCESS, BusinessCode.SUCCESS.name(), message);
    }

    public static BaseResponse failure(String message) {
        return codeBaseResponse(SystemCode.FAILURE, (String)null, message);
    }

    public static BaseResponse codeBaseResponse(SystemCode systemCode, String businessCode, String message) {
        ResponseHeader responseHeader = ResponseHeader.builder().systemCode(systemCode).businessCode(businessCode).message(message).build();
        return new BaseResponse(responseHeader);
    }

    public static BaseResponse exception(Throwable throwable) {
        return exceptionBaseResponse(throwable, false, (Function)null);
    }

    public static BaseResponse exception(Throwable throwable, boolean enableTrace, Function<Throwable, ResponseHeaderBuilder> customizedExceptionHeader) {
        return BaseResponse.builder().header(ResponseHeaderUtils.exceptionHeader(throwable, enableTrace, customizedExceptionHeader)).build();
    }
}
