package com.zl.wise.common.rpc.response;

import java.io.Serializable;
import java.util.List;

public class ListBodyResponse <T extends Serializable> extends BaseResponse {
    private List<T> body;
}
