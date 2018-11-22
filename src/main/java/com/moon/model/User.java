package com.moon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Author : moon
 * Date  : 2018/11/21 16:00
 * Description : Class for
 */
@Data
public class User extends BasicModel {

    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private Integer status;
}
