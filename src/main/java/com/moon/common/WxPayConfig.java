package com.moon.common;

/**
 * Author : moon
 * Date  : 2018/12/5 15:04
 * Description : Class for 微信支付参数设置
 */
public class WxPayConfig {

    public WxPayConfig() {
    }

    /**
     * APP_APP_ID 应用ID
     */
    public static final String APP_APP_ID = "";
    /**
     * APP_MCH_ID 商户号
     */
    public static final String APP_MCH_ID = "";
    /**
     * APP_API_KEY 密钥
     */
    public static final String APP_API_KEY = "";
    /**
     * APP_NOTIFY_URL 回调地址
     */
    public static final String APP_NOTIFY_URL = "";
    /**
     * APP_ORDER_URL 微信支付——统一下单接口网关
     */
    public static final String APP_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * APP_ORDER_QUERY_URL 微信支付——订单查询接口网关
     */
    public static final String APP_ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * DOWN_LOAD_BILL_ORDER 微信支付——下载对账单接口网关
     */
    public static final String DOWNLOAD_BILL_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";

}
