package com.zl.wise.common.rpc.response;

import com.zl.wise.common.rpc.i18n.I18nMessage;

import java.io.Serializable;

public class ResponseHeader  implements Serializable {
    @NotNull
    private SystemCode systemCode;
    private String businessCode;
    private String trace;
    private String message;
    private I18nMessage i18nMessage;
}
