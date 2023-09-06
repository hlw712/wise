package com.zl.wise.common.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>响应头</p>
 *
 * @author huanglinwei
 * @date 2023/9/4 21:20
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header {

    private String code;

    private String msg;
}
