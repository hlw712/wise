package com.zl.wise.common.rpc.request;

import java.io.Serializable;
import java.util.List;

public class ListBodyRequest<T extends Serializable> extends BaseRequest {
    private List<T> body;
}
