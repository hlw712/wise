package com.zl.wise.common.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "响应码", required = true, example = "200")
    private String code;

    @ApiModelProperty(value = "响应信息", required = true, example = "ok")
    private String msg;

}
