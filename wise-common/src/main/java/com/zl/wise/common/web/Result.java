package com.zl.wise.common.web;

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
public class Result<T> {

    private Header head;

    private T body;

    public static <T> Result<T> success(T data) {
        return new Result<>(Header.builder().code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getMessage()).build(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(Header.builder().code(ResultEnum.SUCCESS.getCode()).msg(message).build(), data);
    }

    public static Result<?> failed() {
        return new Result<>(Header.builder().code(ResultEnum.COMMON_FAILED.getCode()).msg(ResultEnum.COMMON_FAILED.getMessage()).build(), null);
    }

    public static Result<?> failed(String message) {
        return new Result<>(Header.builder().code(ResultEnum.COMMON_FAILED.getCode()).msg(message).build(), null);
    }

    public static Result<?> failed(IResult errorResult) {
        return new Result<>(Header.builder().code(errorResult.getCode()).msg(errorResult.getMessage()).build(), null);
    }

    public static <T> Result<T> instance(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.setHead(Header.builder().code(code).msg(message).build());
        result.setBody(data);
        return result;
    }
}
