package com.moon.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Author : moon
 * Date  : 2018/11/21 13:55
 * Description : Class for 获取资源文件内容
 */
public class PropertiesUtil {

    private final ResourceBundle resource;
    private final String fileName;

    /**
     * 构造实例化部分对象，获取文件资源
     *
     * @param fileName
     */
    public PropertiesUtil(String fileName) {
        this.fileName = fileName;
        Locale locale = new Locale("zh", "CN");
        this.resource = ResourceBundle.getBundle(this.fileName, locale);
    }

    /**
     * 根据key获取value值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return this.resource.getString(key);
    }

    /**
     * 获取所有KEY
     *
     * @return
     */
    public Enumeration<String> getKeys() {
        return this.resource.getKeys();
    }


}
