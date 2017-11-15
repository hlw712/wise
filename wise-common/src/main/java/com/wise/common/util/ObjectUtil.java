package com.wise.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对象处理类
 *
 * @author huanglw
 * @date 2017/11/15
 * @updater
 * @version V1.0.0
 *
 * @remarks:
 */
public class ObjectUtil {
    /**
     * 判断对象是否为空，包括数组、集合、map等是否为空
     *
     * @param o java.lang.Object 输入对象
     * @return boolean 为空则返回true
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyAll(Object o) {

        if (null == o) {
            return true;
        }
        // 如果是字符串
        if (o instanceof CharSequence) {
            return !org.springframework.util.StringUtils.hasText((CharSequence) o);
        }
        // 如果是集合
        if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        }
        // 如果是Map
        if (o instanceof Map) {
            return ((Map) o).isEmpty();
        }
        // 如果是类对象
        if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断数组中对象是否有重复对象
     *
     * @param objs java.lang.Object 输入对象数组
     * @return boolean 如果重复返回true
     */
    public static boolean isHaveRepeat(Object[] objs)
    {
        Set<Object> set = new HashSet<Object>();
        for (Object o : objs) {
            set.add(o);
        }

        return set.size() < objs.length;
    }
}
