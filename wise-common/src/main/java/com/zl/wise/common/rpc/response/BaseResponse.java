package com.zl.wise.common.rpc.response;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    @NotNull
    @Valid
    private ResponseHeader header;
}
