package com.moon.exception;

import com.moon.enums.ExceptionEnum;

/**
 * Author : moon
 * Date  : 2018/12/11 11:16
 * Description : Class for 自定义异常
 */
public class MyException extends RuntimeException {

    private int code;

    public MyException() {
        super();
    }

    public MyException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public MyException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
