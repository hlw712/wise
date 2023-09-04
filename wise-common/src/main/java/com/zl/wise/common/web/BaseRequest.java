package com.zl.wise.common.web;

import com.uaepay.merchant.console.frontend.common.enums.ResponseCode;
import com.uaepay.merchant.console.frontend.common.exception.BusinessLogicException;
import io.bitexpress.topia.commons.rpc.error.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>基础请求</p>
 *
 * @author huanglinwei
 * @date 2023/9/4 21:20
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BaseResponse")
public class BaseRequest<T> {

    @ApiParam("head")
    private Header head;

    @ApiParam("Body")
    private T body;

    public static <K> BaseRequest<K> build(String code, String msg, K body) {
        return BaseRequest.<K>builder()
                .head(Header.builder().code(code).msg(msg).build())
                .body(body).build();
    }

    public static <K> BaseRequest<K> success() {
        return success(null);
    }

    public static <K> BaseRequest<K> success(K body) {
        return build(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), body);
    }

    public static <K> BaseRequest<K> failBySystemError(K body) {
        return build(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getMessage(), body);
    }

    public static <K> BaseRequest<K> failBySystemError() {
        return build(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getMessage(), null);
    }

    public static <K> BaseRequest<K> failByMsg(String msg, K body) {
        return build(ResponseCode.BUSINESS_EXCEPTION.getCode(), msg, body);
    }

    public static <K> BaseRequest<K> failByBusinessCode(ResponseCode exceptionCode) {
        return build(exceptionCode.getCode(), exceptionCode.getMessage(), null);
    }

    public static <K> BaseRequest<K> failByBusinessException(BusinessLogicException exception) {
        return build(exception.getExceptionCode().getCode(), exception.getExceptionCode().getMessage(), null);
    }

    public static <K> BaseRequest<K> failByErrorCode(ErrorCode errorCode, K body) {
        return build(ResponseCode.BUSINESS_EXCEPTION.getCode(), errorCode.getCode() + ":" + errorCode.getTemplate(), body);
    }
}
