package com.zl.wise.common.concept.time;

import java.io.Serializable;
import java.util.Date;

public class TimePoint implements Serializable {
    @NotNull
    private PointInclusion inclusion;
    @NotNull
    private Date time;
}
