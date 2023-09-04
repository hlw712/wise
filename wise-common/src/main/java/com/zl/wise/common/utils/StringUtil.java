package com.zl.wise.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class StringUtil {

    /**
     * 判断字符串是否为null，""，以及"    "
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(@Nullable Object str) {
        boolean flag = StringUtils.isEmpty(str);
        if (!flag) {
            flag = StringUtils.isEmpty(String.valueOf(str).trim());
        }
        return flag;
    }

    public static String replaceNullObj(String resource, String replaceObj) {
        return resource == null || "".equals(resource) ? replaceObj : resource;
    }
}
