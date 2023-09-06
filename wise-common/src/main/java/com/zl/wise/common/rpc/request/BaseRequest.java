package com.zl.wise.common.rpc.request;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {
    @Valid
    private RequestHeader header;
}
