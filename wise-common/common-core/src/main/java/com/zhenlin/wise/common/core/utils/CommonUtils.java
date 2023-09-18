package com.zhenlin.wise.common.core.utils;

/**
 * 功能描述
 *
 * @author huanglinwei
 * @date 2021/11/8 7:26 下午
 * @version 1.0.0
 */
public class CommonUtils {

    public static String DEFAULT_REPLACE_VAL = "";

    public static String replaceNullObj(Object resource) {
        return resource == null ? DEFAULT_REPLACE_VAL : resource+"";
    }

    public static Object replaceNullObj(Object resource, Object replaceObj) {
        return resource == null ? replaceObj : resource;
    }
}
