package com.zl.wise.common.pagination;

import java.io.Serializable;

public class SortParam implements Serializable {
    @NotNull
    private Direction direction;
    @NotBlank
    private String property;

}
