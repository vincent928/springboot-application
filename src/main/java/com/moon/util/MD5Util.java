package com.moon.util;

import java.security.MessageDigest;

/**
 * Author : moon
 * Date  : 2018/11/21 18:02
 * Description : Class for MD5加密信息
 */
public class MD5Util {

    private static final String SALT = "F8759288724160F13B8AF586FA82F48E"; //moon@163.com
    private static final String CHARSET_NAME = "UTF-8";
    private static final String[] HEX_DIGITS = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 默认MD5+SALT加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String getMD5(String data) throws Exception {
        return encodeMd5(data, SALT);
    }

    /**
     * MD5加密
     *
     * @param data
     * @param saltIsUse 是否加盐
     * @return
     */
    public static String getMD5(String data, boolean saltIsUse) throws Exception {
        if (saltIsUse) {
            return encodeMd5(data, SALT);
        } else {
            return encodeMd5(data, null);
        }
    }


    private static String encodeMd5(String str, String salt) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        StringBuffer bf = new StringBuffer();
        byte[] digest;
        if (salt == null || "".equals(salt)) {
            digest = md5.digest(str.getBytes(CHARSET_NAME));
        } else {
            str = str + salt;
            digest = md5.digest(str.getBytes(CHARSET_NAME));
        }
        for (byte b : digest) {
            bf.append(byteToHexString(b));
        }
        return bf.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

}
