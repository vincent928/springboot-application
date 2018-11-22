package com.moon.common;

import com.moon.enums.CodeEnum;

/**
 * Author : moon
 * Date  : 2018/11/21 16:55
 * Description : Class for 返回结果集封装类
 */
public class ResultData {

    private Integer code;
    private String msg;
    private Object data;


    private ResultData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Object getData() {
        return this.data;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ResultData success(Integer code) {
        return new ResultData(code, "", null);
    }

    public static ResultData success(Object data) {
        return new ResultData(CodeEnum.SUCCESS.getCode(), "", data);
    }

    public static ResultData success(Integer code, String msg) {
        return new ResultData(code, msg, null);
    }

    public static ResultData success(Integer code, Object data) {
        return new ResultData(code, "", data);
    }

    public static ResultData success(String msg, Object data) {
        return new ResultData(CodeEnum.SUCCESS.getCode(), msg, data);
    }


    public static ResultData success(Integer code, String msg, Object data) {
        return new ResultData(code, msg, data);
    }


    public static ResultData error() {
        return new ResultData(CodeEnum.FAIL.getCode(), "", null);
    }

    public static ResultData error(String msg) {
        return new ResultData(CodeEnum.FAIL.getCode(), msg, null);
    }

    public static ResultData miss() {
        return new ResultData(CodeEnum.DATA_MISS.getCode(), "", null);
    }

    public static ResultData miss(String msg) {
        return new ResultData(CodeEnum.DATA_MISS.getCode(), msg, null);
    }


}
