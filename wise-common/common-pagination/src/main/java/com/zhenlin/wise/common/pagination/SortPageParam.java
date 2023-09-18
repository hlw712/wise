package com.zhenlin.wise.common.pagination;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class SortPageParam extends PageParam {
    @Valid
    private List<SortParam> sortParamList;
}
