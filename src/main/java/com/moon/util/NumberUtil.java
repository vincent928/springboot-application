package com.moon.util;

import org.apache.commons.lang3.RandomUtils;

/**
 * Author : moon
 * Date  : 2018/11/21 13:40
 * Description : Class for 数字工具类
 */
public class NumberUtil {

    /**
     * 生成4位数验证码
     *
     * @return
     */
    public static String getPhoneCode() {
        return String.valueOf(RandomUtils.nextInt(1000, 10000));
    }

    /**
     * 生成验证码(指定位数)
     *
     * @return
     */
    public static String getPhoneCode(int size) {
        String code = String.valueOf(RandomUtils.nextInt((int) Math.pow(10, size - 1) - 1, (int) Math.pow(10, size)));
        return code;
    }

}
