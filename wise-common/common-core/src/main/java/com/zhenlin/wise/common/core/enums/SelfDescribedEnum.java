package com.zhenlin.wise.common.core.enums;

public interface SelfDescribedEnum {

    default String getName(){
        return name();
    }

    String name();

    String getDescription();
}
