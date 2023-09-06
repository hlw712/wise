package com.zl.wise.common.rpc.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyResponse <T extends Serializable> extends BaseResponse {
    private T body;
}
