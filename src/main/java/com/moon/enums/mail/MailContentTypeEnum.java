package com.moon.enums.mail;

/**
 * Author : moon
 * Date  : 2018/11/21 13:36
 * Description : Class for 邮件内容枚举类
 */
public enum MailContentTypeEnum {

    HTML("text/html;charset=UTF-8"),
    TEXT("text");

    private String value;

    MailContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
