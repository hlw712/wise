package com.zl.wise.common.web;

import java.io.Serializable;
import java.util.Locale;

public class RequestHeader implements Serializable {
    public static final RequestHeader EMPTY_HEADER = new RequestHeader();
    private String sourceCode;
    @Valid
    private Issuer issuer;
    private Cacheable cacheable;
    private Locale locale;
}
