package com.moon.model.email;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.model.BasicModel;
import lombok.Data;

import java.util.Date;

/**
 * Author : moon
 * Date  : 2018/11/23 10:51
 * Description : Class for
 */
@Data
public class TbMailModel extends BasicModel {

    private Integer userId;
    private String mail;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date updateTime;
    private Integer status;


}
