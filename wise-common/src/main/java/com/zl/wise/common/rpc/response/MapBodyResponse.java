package com.zl.wise.common.rpc.response;

import java.util.Map;

public class MapBodyResponse<K, V> extends BaseResponse {
    private Map<K, V> body;
}
