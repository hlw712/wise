package com.zl.wise.common.rpc.response;

import java.io.Serializable;

public class BodyResponse <T extends Serializable> extends BaseResponse {
    private T body;
}
