package com.zl.wise.common.rpc.utils;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class ListBodyResponseUtils<T> {
    private static final Logger logger = LoggerFactory.getLogger(ListBodyResponseUtils.class);

    public ListBodyResponseUtils() {
    }

    public static <T extends Serializable> ListBodyResponse<T> codeListResultResponse(List<T> bodyList, SystemCode systemCode, String businessCode, String message) {
        ResponseHeader responseHeader = ResponseHeader.builder().systemCode(systemCode).businessCode(businessCode).message(message).build();
        return ((ListBodyResponseBuilder)ListBodyResponse.listBodyBuilder().header(responseHeader)).body(bodyList).build();
    }

    public static <T extends Serializable> ListBodyResponse<T> successListBodyResponse(List<T> bodyList) {
        return codeListResultResponse(bodyList, SystemCode.SUCCESS, BusinessCode.SUCCESS.name(), (String)null);
    }

    public static <T extends Serializable> ListBodyResponse<T> failureBodyResponse(String message) {
        return codeListResultResponse((List)null, SystemCode.FAILURE, (String)null, message);
    }

    public static <T extends Serializable> ListBodyResponse<T> exceptionListBodyResponse(Throwable throwable) {
        return exceptionListBodyResponse(throwable, false, (Function)null);
    }

    public static <T extends Serializable> ListBodyResponse<T> exceptionListBodyResponse(Throwable throwable, boolean enableTrace, Function<Throwable, ResponseHeaderBuilder> customizedExceptionHeader) {
        return ((ListBodyResponseBuilder)ListBodyResponse.listBodyBuilder().header(ResponseHeaderUtils.exceptionHeader(throwable, enableTrace, customizedExceptionHeader))).build();
    }

    public static <T extends Serializable> List<T> parse(ListBodyResponse<T> listResultResponse, String... silentBusinessCodes) {
        BaseResponseUtils.parse(listResultResponse, silentBusinessCodes);
        return listResultResponse.getBody();
    }
}
