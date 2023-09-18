package com.zhenlin.wise.common.core.concept.time;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class TimePoint implements Serializable {
    @NotNull
    private PointInclusion inclusion;
    @NotNull
    private Date time;
}
