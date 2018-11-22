package com.moon.enums;

/**
 * Author : CaoXm
 * Date  : 2018/11/21 16:58
 * Description : Enum for 状态码
 */
public enum CodeEnum {

    SUCCESS(2000),
    DATA_MISS(1005),
    FAIL(1000),
    ;

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
