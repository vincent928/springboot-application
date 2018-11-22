package com.moon.api.yunpian;

import lombok.Data;

/**
 * Author : moon
 * Date  : 2018/11/22 11:57
 * Description : Class for 请求封装类
 */
@Data
public class YpHttpModel {

    //描述                                是否必须      是否默认开放
    //用户唯一标识，在管理控制台获取          是            是
    private String apikey;
    //接收的手机号，仅支持单号码发送          是            是
    private String mobile;
    //已审核短信模板                         是             是
    private String text;
    //下发号码扩展号，纯数字                  否            否
    private String extend;
    //该条短信在您业务系统内的ID              否             否
    private String uid;
    //短信发送后将向这个地址推送发送报告       否             是
    private String callback_url;
    //是否为注册验证码短信 统计注册成功率      否             否
    private boolean register;
    //用于统计哪些号码的机主点击了短信中的链接   否              是
    private boolean mobile_stat;
    //注意：如需使用"extend""uid"参数，可免费向客服申请。
}
