package com.wise.common.security;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: linwei
 * Date: 15-5-25
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class SHACoder {

    private static final String[] HEX_DIGITS =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    private static final int SIXTEEN = 16;
    private static final String SHA_256 = "SHA-256";


    /**
     * SHA-1消息摘要算法
     */
    public static String encodeSHA(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称
        MessageDigest md = MessageDigest.getInstance("SHA");
        // 执行摘要方法
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-256消息摘要算法
     */
    public static String encodeSHA256(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称
        MessageDigest md = MessageDigest.getInstance(SHA_256);
        // 执行摘要方法
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-384消息摘要算法
     */
    public static String encodeSHA384(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        // 执行摘要方法
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-512消息摘要算法
     */
    public static String encodeSHA512(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        // 执行摘要方法
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-256 加密
     *
     * @param data data
     * @return String
     */
    public static String encryptSHA(String data){

        if (null == data || "".equals(data)){
            return data;
        }
        MessageDigest mDigest;

        try {
            mDigest = MessageDigest.getInstance(SHA_256);
            data = byteArrayToHexString(mDigest.digest(data.getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String byteArrayToHexString(byte[] b) {

        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / SIXTEEN;
        int d2 = n % SIXTEEN;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static void main(String[] args) throws Exception {
        String testString="asd`12asd31";
        // 密码=用户名+密码+随机数
        System.out.println(SHACoder.encodeSHA(testString.getBytes()));
        System.out.println(SHACoder.encodeSHA256(testString.getBytes()));
        System.out.println(SHACoder.encodeSHA384(testString.getBytes()));
        System.out.println(SHACoder.encodeSHA512(testString.getBytes()));
    }
}
