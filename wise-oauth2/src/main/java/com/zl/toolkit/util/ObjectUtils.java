package com.zl.toolkit.util;

import java.util.UUID;

/**
 * Created by hlw on 2017/8/21.
 */
public class ObjectUtils {

    /***
     * 得到uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /***
     * 得到uuid
     *
     * @return
     */
    public static String getUUIDReplace() {
        return getUUID().replace("-", "");
    }

    public static boolean isNull(Object obj) {
        return (null == obj);
    }


}
