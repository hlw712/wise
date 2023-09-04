package com.zl.wise.common.pagination;

import java.io.Serializable;

public class PageParam implements Serializable {
    @Min(0L)
    private int number;
    @Min(1L)
    private int size;
}
