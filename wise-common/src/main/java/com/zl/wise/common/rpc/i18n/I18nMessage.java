package com.zl.wise.common.rpc.i18n;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I18nMessage implements Serializable {
    private String key;
    private Serializable[] params;
}
