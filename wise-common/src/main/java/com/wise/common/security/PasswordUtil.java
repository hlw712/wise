package com.wise.common.security;

/**
 * 模块描述
 *
 * @author huanglw
 * @version 1.0.0
 * @see // 引用的类
 * Method List:
 * 1.----------------
 * 2.----------------
 * History:
 */
public class PasswordUtil {


    public static String encodePassword(String userName, String pwd) {
        String salt="_gas_pipe_";//加盐
        return SHACoder.encryptSHA(userName+pwd+salt);
    }
}
