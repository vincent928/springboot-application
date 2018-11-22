package com.moon.api.yunpian;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import java.util.Map;

/**
 * Author : moon
 * Date  : 2018/11/22 11:53
 * Description : Class for 云片短信接口调用
 */
public class YpMessageApi {

    //账户信息地址
    private static final String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";
    //智能匹配模板发送接口的地址
    private static final String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
    //模板发送接口的地址
    private static final String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
    //发送语音验证码的地址
    private static final String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";
    //编码格式
    private static final String ENCODING = "UTF-8";
    //apikey
    private static final String API_KEY = "";

    /**
     * 单条短信发送
     *
     * @param model
     * @return
     */
    public static void sendSMS(YpHttpModel model) {
        //初始化client，
        //   YunpianClient client = new YunpianClient(model.getApikey()).init();
        YunpianClient client = new YunpianClient(API_KEY).init();
        Map<String, String> param = client.newParam(2);
        param.put(YunpianClient.MOBILE, model.getMobile());
        param.put(YunpianClient.TEXT, model.getText());
        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().*
        // 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
        Result<SmsSingleSend> result = client.sms().single_send(param);
        System.out.println("{\n[短信CODE]:" + result.getCode() + "\n[短信内容]:" + result.getData()
                + "\n[短信详情]:" + result.getDetail() + "\n[短信错误]:" + result.getThrowable() + "\n}");
        //释放client
        client.close();
    }

}
