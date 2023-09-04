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
 * <p>基础响应</p>
 *
 * @author huanglinwei
 * @date 2023/9/4 21:20
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    private Header head;

    private T body;

    public static <K> BaseResponse<K> build(String code, String msg, K body) {
        return BaseResponse.<K>builder()
                .head(Header.builder().code(code).msg(msg).build())
                .body(body).build();
    }

    public static <K> BaseResponse<K> success() {
        return success(null);
    }

    public static <K> BaseResponse<K> success(K body) {
        return build(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), body);
    }

    public static <K> BaseResponse<K> failBySystemError(K body) {
        return build(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getMessage(), body);
    }

    public static <K> BaseResponse<K> failBySystemError() {
        return build(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getMessage(), null);
    }

    public static <K> BaseResponse<K> failByMsg(String msg, K body) {
        return build(ResponseCode.BUSINESS_EXCEPTION.getCode(), msg, body);
    }

    public static <K> BaseResponse<K> failByBusinessCode(ResponseCode exceptionCode) {
        return build(exceptionCode.getCode(), exceptionCode.getMessage(), null);
    }

    public static <K> BaseResponse<K> failByBusinessException(BusinessLogicException exception) {
        return build(exception.getExceptionCode().getCode(), exception.getExceptionCode().getMessage(), null);
    }

    public static <K> BaseResponse<K> failByErrorCode(ErrorCode errorCode, K body) {
        return build(ResponseCode.BUSINESS_EXCEPTION.getCode(), errorCode.getCode() + ":" + errorCode.getTemplate(), body);
    }
}
