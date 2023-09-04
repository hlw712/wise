package com.zl.wise.common.rpc.request;

import java.io.Serializable;

public class Issuer implements Serializable {
    @NotBlank
    private String code;
    @NotBlank
    private String type;
}
