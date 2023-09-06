package com.zl.wise.common.rpc.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeader implements Serializable {
    public static final RequestHeader EMPTY_HEADER = new RequestHeader();
    private String sourceCode;
    @Valid
    private Issuer issuer;
    private Cacheable cacheable;
    private Locale locale;
}
