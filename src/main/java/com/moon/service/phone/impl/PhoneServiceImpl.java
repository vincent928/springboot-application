package com.moon.service.phone.impl;

import com.moon.api.yunpian.YpHttpModel;
import com.moon.api.yunpian.YpMessageApi;
import com.moon.common.ResultData;
import com.moon.mapper.PhoneMapper;
import com.moon.model.phone.PhoneModel;
import com.moon.service.phone.PhoneService;
import com.moon.util.AssertUtil;
import com.moon.util.NumberUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author : moon
 * Date  : 2018/11/22 13:59
 * Description : Class for phoneService
 */
@Service
public class PhoneServiceImpl implements PhoneService {

    @Resource
    private PhoneMapper phoneMapper;
    /**
     * 获取短信验证码
     *
     * @param phone
     * @return
     */
    @Override
    public ResultData getPhoneCode(String phone) {
        if (AssertUtil.assertPhoneNum(phone)) {
            return ResultData.error("手机号码格式错误");
        }
        //1、号码是否重复
        PhoneModel p = phoneMapper.findPhoneByNum(phone);
        if (p != null) {
            return ResultData.error("该手机号已经注册");
        }
        //2、生成code验证码
        PhoneModel model = new PhoneModel();
        model.setCode(NumberUtil.getPhoneCode());
        model.setPhone(phone);
        if (phoneMapper.addNewPhone(model)>0){
            //3、发送验证码(异步/同步) 若有消息队列，可以利用MQ
            //暂时用线程处理
            ExecutorService ex = Executors.newFixedThreadPool(1);
            ex.submit(()->{
                YpHttpModel ypHttpModel = new YpHttpModel();
                ypHttpModel.setMobile(phone);
                ypHttpModel.setText("");
                YpMessageApi.sendSMS(ypHttpModel);
            });
            return ResultData.success("短信验证码已发送");
        }
        return ResultData.error("系统错误,请联系客服");
    }
}
