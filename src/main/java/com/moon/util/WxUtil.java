package com.moon.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Author : moon
 * Date  : 2018/12/4 11:41
 * Description : Class for wxpay util
 */
public class WxUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(WxUtil.class);

    private final static String APP_API_KEY = "";
    private final static String APP_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private final static String APP_ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    private final static String DOWNLOAD_BILL_ORDER = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 解析微信发来的请求（XML）
     * <xml>
     * <ToUserName><![CDATA[gh_3f0de39920ff]]></ToUserName>
     * <Encrypt><![CDATA[g1gyDGk4Vq5BaOpVrJF768L3Zj2IT8IriVFWoGgt+fIa+R1oYRXYIGQiyaUf9d2yvlr7jWg8stNfTsg6ENz6
     * /BXYYwy5ui8158USOfwlngthH2w95mysri9lZ7QfKwybueYHJdPWBexv2q9JXGRejegg7aDVMNNTIaHAQGnCRThcD5xwjoEKYEN4bagdqxvnbPwCXQq1cZVyDuGg8AE2IOeECDmFocY17F19mIJFfeS1wnP3mwOM/DRItcpxK6u2F0xHZFIVLR+8K3PKy0d53/QLBhIjAmFz0ykEdlpAohe29pZ3oezvVLz3I2fRaxphktXVRIytS5ktp1c2BiPcHmB67ypT6qMB9rgqVnjMwLtjBJb1Hs0G0k63LG6MFVZuraSJz9AAxTYxp47ITNAsPwP+7vzz7m15aH71eqy9Zk4=]]></Encrypt>
     * </xml>
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String parsePostMsgBody(HttpServletRequest request) throws Exception {
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(bufferedReader);
            return document.asXML();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Xml转Map
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, String> xmlString2Map(String xmlStr) {
        Map<String, String> map = new TreeMap<String, String>(String::compareTo);
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }

    /**=========================================================
     *                        支付                             *
     =========================================================*/

    /**
     * createTimestamp
     *
     * @return
     */
    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 随机生成32位字符串
     *
     * @return
     */
    public static String getRandom32Str() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * Map转换成Xml
     *
     * @param map
     * @return
     */
    public static String map2XmlString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set<String> set = map.keySet();
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = map.get(key);
            sb.append("<").append(key).append(">");
            sb.append("<![CDATA[" + value + "]]>");
            sb.append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> inputStreamToMap(HttpServletRequest request) throws Exception {
        return xmlString2Map(parsePostMsgBody(request));
    }

    /**
     * 响应成功
     *
     * @return
     */
    public static String responseSuccess() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");
        return WxUtil.map2XmlString(map);
    }

    /**
     * 响应失败
     *
     * @return
     */
    public static String responseFail() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("return_code", "FAIL");
        map.put("return_msg", "");
        return WxUtil.map2XmlString(map);
    }


    /**
     * APP支付加签
     *
     * @param params 待加签参数
     * @return 返回签名
     */
    public static String executeAppSign(Map<String, String> params) {
        return executeSign(params, APP_API_KEY);
    }

    /**
     * 加签
     *
     * @param params 待加签参数
     * @param key    签名
     * @return 返回签名
     */
    private static String executeSign(Map<String, String> params, String key) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!StringUtils.isBlank(entry.getValue())) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("key=").append(key);
        }
        String stringSignTemp = stringBuilder.toString();
        return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
    }


    /**
     * 解析微信对账单
     *
     * @return
     */
    public static String[] parseBill(String bill) {
        try {
            String s = bill.substring(bill.indexOf("`"));
            String replace = s.substring(0, s.indexOf("总")).replace("`", "");
            String[] split = replace.split("\n");
            return split;
        } catch (Exception e) {
            LOGGER.info("解析对账单信息出错:" + e.getMessage());
            return null;
        }
    }


    /**---------------------------------------------------------------------
     *                                                                     *
     *                       微信接口调用                                 *
     *                                                                  *
     ------------------------------------------------------------------*/

    /**
     * 调用微信统一下单接口,并完成验签
     *
     * @param paramsMap
     * @return
     */
    public static Map<String, String> httpPayOrder(Map<String, String> paramsMap) throws Exception {
        String appSign = executeAppSign(paramsMap);
        paramsMap.put("sign", appSign);
        String reqXmlStr = map2XmlString(paramsMap);
        String post = HttpUtil.httpMethodPost(APP_ORDER_URL, reqXmlStr, "UTF-8");
        Map<String, String> resMap = xmlString2Map(post);
        if (resMap == null || resMap.isEmpty()) {
            throw new Exception();
        }
        String returnCode = resMap.get("return_code");
        String resultCode = resMap.get("result_code");
        String prepayId = resMap.get("prepay_id");
        String wxSign = resMap.get("sign");
        //验签
        if (!"SUCCESS".equals(returnCode) || !"SUCCESS".equals(resultCode) || StringUtils.isBlank(prepayId)
                || StringUtils.isBlank(wxSign) || !wxSign.equals(appSign)) {
            throw new Exception();
        }
        return resMap;
    }

    /**
     * 调用微信订单查询接口,并完成验签
     * https://api.mch.weixin.qq.com/pay/orderquery
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     *
     * @param params
     * @return
     */
    public static Map<String, String> httpOrderQuery(Map<String, String> params) throws Exception {
        String sign = WxUtil.executeAppSign(params);
        params.put("sign", sign);
        String xmlString = WxUtil.map2XmlString(params);
        String response = HttpUtil.httpMethodPost(APP_ORDER_QUERY_URL, xmlString, "UTF-8");
        Map<String, String> map = WxUtil.xmlString2Map(response);
        if (map == null || map.isEmpty()) {
            throw new Exception();
        }
        String return_code = map.get("return_code");
        String result_code = map.get("result_code");
        String wxSign = map.get("sign");
        if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code) && !StringUtils.isBlank(wxSign)) {
            //验签
            if (!sign.equals(wxSign)) {
                throw new Exception();
            }
            //TODO:拼装订单信息
            Map<String, String> infoMap = new HashMap<>();
            infoMap.put("status", map.get("trade_state"));
            infoMap.put("totalfee", map.get("total_fee"));
            infoMap.put("transactionid", map.get("transaction_id"));
            infoMap.put("outtradeno", map.get("out_trade_no"));
            infoMap.put("timeend", map.get("time_end"));
            return infoMap;
        }
        throw new Exception();
    }

    /**
     * 微信对账单下载接口调用
     * https://api.mch.weixin.qq.com/pay/downloadbill
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为"元"。
     * 4、对账单接口只能下载三个月以内的账单。
     * 5、对账单是以商户号纬度来生成的，如一个商户号与多个appid有绑定关系，则使用其中任何一个appid都可以请求下载对账单。
     * 对账单中的appid取自交易时候提交的appid，与请求下载对账单时使用的appid无关。
     *
     * @param params
     * @return
     */
    public static String httpBillDownload(Map<String, String> params) throws Exception {
        String sign = executeAppSign(params);
        params.put("sign", sign);
        String xmlString = map2XmlString(params);
        String response = HttpUtil.httpMethodPost(DOWNLOAD_BILL_ORDER, xmlString, "UTF-8");
        //失败则返回xml,否则为文本数据
        Map<String, String> map = xmlString2Map(response);
        if (map != null && !map.isEmpty()) {
            throw new Exception(map.get("return_msg"));
        }
        return response;
    }

}
