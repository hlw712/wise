package com.zl.wise.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 掩码工具包
 *
 * @author huanglinwei
 * @date 2020/11/18 10:42 PM
 * @version 1.0.0
 */
public class MaskedCodeUtils {

    /**
     * 获取手机号掩码
     *
     * @param telephone
     * @return
     */
    public static String getTelephoneMasked(String telephone) {
        if(StringUtils.isBlank(telephone)) {
            return telephone;
        }
        String result;
        if (telephone.length() > 4) {
            if (telephone.contains("-")) {
                String[] temp = telephone.split("-");
                result = temp[0] + "-" + temp[1].substring(0,1) +"****" + temp[1].substring(temp[1].length() - 4, temp[1].length());
            } else {
                result = telephone.substring(0, 3) + "****" + telephone.substring(telephone.length() - 4, telephone.length());
            }
        } else {
            result = "****" + telephone.substring(telephone.length() - 4, telephone.length());
        }

        return result;
    }

    /**
     * 获取email掩码
     *
     * @param email
     * @return
     */
    public static String getEmailMasked(String email) {
        // 如果email为空，或者不是email字符串，那么返回该字符串
        if(StringUtils.isBlank(email) || !ValidateUtil.isEmail(email)) {
            return email;
        }
        String[] temp = email.split("@");
        char[] emailNames = temp[0].toCharArray();
        StringBuilder stringBuilder = new StringBuilder(16);
        for (int i = 0; i < emailNames.length; i++) {
            if (i < 3) {
                stringBuilder.append(emailNames[i]);
            } else {
                stringBuilder.append("*");
            }
        }
        return stringBuilder.toString() + "@" + temp[1];
    }



    /**
     * 掩码工具类
     *
     * @author huanglinwei
     * @date 2021/12/27 6:37 下午
     * @version 1.0.0
     */
    public static String mask(String msg, Integer startIndexOf, Integer endIndexOf, String c) {
        if (msg == null || "".equals(msg)) {
            return msg;
        }
        char[] msgList = msg.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(16);
        for (int i = 1; i <= msgList.length; i++) {
            if (i >= startIndexOf && i <= endIndexOf) {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append(msgList[i-1]);
        }
        return stringBuilder.toString();
    }
}
