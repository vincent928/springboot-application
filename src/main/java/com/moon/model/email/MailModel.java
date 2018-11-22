package com.moon.model.email;

import com.moon.model.BasicModel;
import lombok.Data;

import java.util.List;

/**
 * Author : moon
 * Date  : 2018/11/21 11:56
 * Description : Class for jmail封装
 */
@Data
public class MailModel extends BasicModel {

    //Smtp服务
    private String smtpService;
    //Smtp端口号
    private String smtpPort;
    //目标邮箱
    private String targetMailAddress;
    //发送邮箱的SMTP口令
    private String targetMailSmtpPwd;
    //邮件标题
    private String title;
    //邮件内容
    private String content;
    //邮件类型(默认为html)
    private String contentType;
    //接收邮件地址集合
    private List<String> list;
}
