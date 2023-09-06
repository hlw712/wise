package com.zl.wise.common.concept;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
public class Scope<T extends Serializable> implements Serializable {
    @Valid
    private T from;
    @Valid
    private T to;
}
