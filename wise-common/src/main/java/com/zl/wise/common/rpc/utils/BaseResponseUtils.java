package com.zl.wise.common.rpc.utils;

import java.util.function.Function;

public class BaseResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(BaseResponseUtils.class);

    public BaseResponseUtils() {
    }

    public static BaseResponse successBaseResponse() {
        return successBaseResponse((String)null);
    }

    public static BaseResponse successBaseResponse(String message) {
        return codeBaseResponse(SystemCode.SUCCESS, BusinessCode.SUCCESS.name(), message);
    }

    public static BaseResponse failureBaseResponse(String message) {
        return codeBaseResponse(SystemCode.FAILURE, (String)null, message);
    }

    public static BaseResponse codeBaseResponse(SystemCode systemCode, String businessCode, String message) {
        ResponseHeader responseHeader = ResponseHeader.builder().systemCode(systemCode).businessCode(businessCode).message(message).build();
        return BaseResponse.builder().header(responseHeader).build();
    }

    public static BaseResponse exceptionBaseResponse(Throwable throwable) {
        return exceptionBaseResponse(throwable, false, (Function)null);
    }

    public static BaseResponse exceptionBaseResponse(Throwable throwable, boolean enableTrace, Function<Throwable, ResponseHeaderBuilder> customizedExceptionHeader) {
        return BaseResponse.builder().header(ResponseHeaderUtils.exceptionHeader(throwable, enableTrace, customizedExceptionHeader)).build();
    }

    public static void parse(BaseResponse baseResponse, String... silentBusinessCodes) {
        ResponseHeader header = baseResponse.getHeader();
        Validate.validState(header.getSystemCode() == SystemCode.SUCCESS, header.getMessage(), new Object[0]);
        String[] successCodeArray = (String[])ArrayUtils.add(silentBusinessCodes, BusinessCode.SUCCESS.name());
        if (!ArrayUtils.contains(successCodeArray, header.getBusinessCode())) {
            if (header.getI18nMessage() != null) {
                throw new I18nErrorCodeException(header.getBusinessCode(), header.getMessage(), header.getI18nMessage());
            } else {
                throw new ErrorCodeException(header.getBusinessCode(), header.getMessage());
            }
        }
    }
}
