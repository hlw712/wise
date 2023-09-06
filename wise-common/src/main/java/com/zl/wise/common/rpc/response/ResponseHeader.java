package com.zl.wise.common.rpc.response;

import com.zl.wise.common.rpc.i18n.I18nMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHeader  implements Serializable {
    @NotNull
    private String code;
    private String businessCode;
    private String trace;
    private String message;
    private I18nMessage i18nMessage;
}
