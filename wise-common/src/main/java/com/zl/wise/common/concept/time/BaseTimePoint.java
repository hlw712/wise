package com.zl.wise.common.concept.time;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class BaseTimePoint implements Serializable {
    @NotNull
    private Date time;
}
