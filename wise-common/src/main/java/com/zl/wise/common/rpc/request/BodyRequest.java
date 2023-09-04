package com.zl.wise.common.rpc.request;

import java.io.Serializable;

public class BodyRequest <T extends Serializable> extends BaseRequest {
    @Valid
    private T body;
}
