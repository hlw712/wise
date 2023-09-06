package com.zl.wise.common.pagination;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SortParam implements Serializable {
    @NotNull
    private Direction direction;
    @NotBlank
    private String property;
}
