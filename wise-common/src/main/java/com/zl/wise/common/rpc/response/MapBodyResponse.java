package com.zl.wise.common.rpc.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapBodyResponse<K, V> extends BaseResponse {
    private Map<K, V> body;
}
