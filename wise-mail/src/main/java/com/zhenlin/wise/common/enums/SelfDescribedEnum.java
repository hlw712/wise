package com.zhenlin.wise.common.enums;

public interface SelfDescribedEnum {

    default String getName(){
        return name();
    }

    String name();

    String getDescription();
}
