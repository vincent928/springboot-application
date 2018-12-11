package com.moon.enums;

/**
 * Author : moon
 * Date  : 2018/12/11 11:18
 * Description : Class for 错误信息枚举类
 */
public enum ExceptionEnum {

    EXCEPTION_1(10001, "表单参数缺失"),
    EXCEPTION_2(10002, "请求地址错误"),
    ;

    private int code;
    private String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
