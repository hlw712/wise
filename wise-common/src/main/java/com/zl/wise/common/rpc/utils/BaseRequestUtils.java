package com.zl.wise.common.rpc.utils;

import com.zl.wise.common.rpc.request.BaseRequest;
import com.zl.wise.common.rpc.request.BodyRequest;
import com.zl.wise.common.rpc.request.ListBodyRequest;

import java.io.Serializable;
import java.util.List;

public class BaseRequestUtils {
    private BaseRequestUtils() {
    }

    public static BaseRequest empty() {
        return BaseRequest.builder().build();
    }

    public static <T extends Serializable> BodyRequest<T> body(T body) {
        return new BodyRequest();
    }

    public static <T extends Serializable> ListBodyRequest<T> listBodyRequest(List<T> listBody) {
        ListBodyRequest listBodyRequest = new ListBodyRequest();
        listBodyRequest.setBody(listBody);
        return listBodyRequest;
    }

    public static <T extends Serializable> BodyRequest<T> listBodyRequest(T body) {
        BodyRequest bodyRequest = new BodyRequest();
        bodyRequest.setBody(body);
        return bodyRequest;
    }
}
