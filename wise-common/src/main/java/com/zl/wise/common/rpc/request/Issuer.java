package com.zl.wise.common.rpc.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class Issuer implements Serializable {
    @NotBlank
    private String code;
    @NotBlank
    private String type;
}
