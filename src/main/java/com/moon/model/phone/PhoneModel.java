package com.moon.model.phone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.model.BasicModel;
import lombok.Data;

import java.util.Date;

/**
 * Author : moon
 * Date  : 2018/11/22 14:07
 * Description : Class for
 */
@Data
public class PhoneModel extends BasicModel {

    private String phone;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date updateTime;
    //0:未验证 1:已验证
    private Integer status;


}
