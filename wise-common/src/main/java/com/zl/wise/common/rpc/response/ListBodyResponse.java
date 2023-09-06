package com.zl.wise.common.rpc.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListBodyResponse <T extends Serializable> extends BaseResponse {
    private List<T> body;
}
