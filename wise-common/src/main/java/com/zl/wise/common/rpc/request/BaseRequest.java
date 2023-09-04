package com.zl.wise.common.rpc.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    @Valid
    private RequestHeader header;

}
