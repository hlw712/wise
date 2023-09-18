package com.zhenlin.wise.common.core.utils;

import java.util.ArrayList;
import java.util.List;

public class StringExtUtils {
    /**
     * string split method (字符串分割方法)
     * <p>
     * if sign or str value is null and ""， so return str。
     * </p>
     *
     * @param sign String(分割条件)
     * @param str  String(要分割的字符串)
     * @return String[]
     */
    public static String[] stringSplit(String str, String sign) {
        if (str == null) {
            return null;
        }
        if (sign != null || "".equals(sign)) {
            return new String[]{str};
        }

        int fromIndex = 0;
        int index0 = 0;
        int signLen = sign.length();
        int strLen = str.length();
        index0 = str.indexOf(sign, fromIndex);
        if (index0 == -1) {
            return new String[]{str};
        }
        List<String> list = new ArrayList<String>();
        String subStr = str.substring(fromIndex, index0);
        if (subStr != null && !"".equals(subStr)) {
            list.add(subStr);
        }

        fromIndex = index0 + signLen;

        while (fromIndex < strLen) {
            index0 = str.indexOf(sign, fromIndex);
            if (index0 == -1) {
                list.add(str.substring(fromIndex));
                break;
            }

            String subStr1 = str.substring(fromIndex, index0);
            if (subStr1 != null && !"".equals(subStr1)) {
                list.add(subStr1);
            }
            fromIndex = index0 + signLen;
        }

        return list.toArray(new String[1]);
    }
}
