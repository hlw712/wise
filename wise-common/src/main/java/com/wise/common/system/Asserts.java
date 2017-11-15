package com.wise.common.system;

import com.wise.common.exception.AssertsException;

/**
 * 业务断言,满足业务条件的方可继续进行后续业务
 *
 * @author huanglw
 */
public class Asserts {

    public static void assertTrue(boolean condition, String errorMessage) {
        if(!condition) {
            throw new AssertsException(errorMessage);
        }
    }

    /**
     * 如果断言为true，那么提示
     *
     * @param condition 如果断言为true，那么提示
     * @param errorMessage
     */
    public static void assertLogic(boolean condition, String errorMessage) {
        if(condition) {
            throw new AssertsException(errorMessage);
        }
    }
}
