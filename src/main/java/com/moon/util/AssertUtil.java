package com.moon.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Author : moon
 * Date  : 2018/11/21 17:28
 * Description : Class for 断言工具 null 或 错误 则返回true,否则返回false
 */
public class AssertUtil {

    private static final boolean ASSERT_TRUE = true;
    private static final boolean ASSERT_FALSE = false;

    private static final String REGEX_PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    /**
     * 断言对象null工具类
     *
     * @param object
     * @return
     */
    public static boolean assertObject(Object object) {
        if (object == null) {
            return ASSERT_TRUE;
        }
        if (object instanceof List) {
            List list = (List) object;
            if (list.size() < 1) {
                return ASSERT_TRUE;
            }
            return ASSERT_FALSE;
        } else if (object instanceof String) {
            String string = (String) object;
            if (StringUtils.isBlank(string)) {
                return ASSERT_TRUE;
            }
            return ASSERT_FALSE;
        } else if (object instanceof Map) {
            Map map = (Map) object;
            if (map.size() < 1) {
                return ASSERT_TRUE;
            }
            return ASSERT_FALSE;
        } else if (object.getClass().isArray()) {
            if (Array.getLength(object) < 1) {
                return ASSERT_TRUE;
            }
            return ASSERT_FALSE;
        }
        return ASSERT_FALSE;
    }


    /**
     * 手机号断言
     *
     * @param phoneNum
     * @return
     */
    public static boolean assertPhoneNum(String phoneNum) {
        if (phoneNum.length() != 11) {
            return ASSERT_TRUE;
        }
        Pattern compile = Pattern.compile(REGEX_PHONE);
        if (compile.matcher(phoneNum).matches()) {
            return ASSERT_FALSE;
        }
        return ASSERT_TRUE;
    }

}
