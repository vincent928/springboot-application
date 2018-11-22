package com.moon.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author : moon
 * Date  : 2018/11/21 17:28
 * Description : Class for 断言工具 null 则返回true,否则返回false
 */
public class AssertUtil {

    private static final boolean ASSERT_TRUE = true;
    private static final boolean ASSERT_FALSE = false;

    /**
     * 断言工具类
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

    public static void main(String[] agrs) {
        String[] a = {"asd", "asdads"};
        Integer[] v = {1, 2, 3, 4, 5};
        Map map = null;
        List<String> j = new ArrayList<>();
        j.add("Ss");
        List<Object> x = new ArrayList<>();
        x.add(a);
        x.add(v);
        x.add(map);
        x.add(j);
        x.forEach((item) -> {
            System.out.println(item + "/" + assertObject(item));
        });
    }
}
