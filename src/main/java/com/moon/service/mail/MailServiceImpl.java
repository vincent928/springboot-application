package com.moon.service.mail;

import com.moon.common.ResultData;
import com.moon.enums.mail.MailContentTypeEnum;
import com.moon.mapper.MailMapper;
import com.moon.model.email.TbMailModel;
import com.moon.util.AssertUtil;
import com.moon.util.NumberUtil;
import com.moon.util.mail.MailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author : moon
 * Date  : 2018/11/23 10:35
 * Description : Class for
 */
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private MailMapper mailMapper;

    private static final String HTTP_URL = "localhost:8888/user/mail/check/active?id={userId}&code={uuid}";
    private static final String MAIL_CONTENT = "您好，感谢您在【Springboot-Application】注册帐户！激活帐户需要点击下面的链接进行验证：\n" +
            "  \t\t{url}  \n\n\n\n\t\t\t\t\t\t\t【Springboot-Application】(www.springboot-application.com) -Moon的邮件\n\n\t\t\t\t\t\t\t\t 代码改变世界！";


    @Override
    public ResultData getMailCheckAddress(Integer userId, String mail) {
        if (AssertUtil.assertObject(userId)) {
            return ResultData.error("用户ID不能为空");
        }
        if (AssertUtil.assertObject(mail)) {
            return ResultData.error("邮箱格式错误");
        }
        //生成UUID码
        String uuid = NumberUtil.getUUID();
        TbMailModel model = new TbMailModel();
        model.setMail(mail);
        model.setCode(uuid);
        model.setUserId(userId);
        try {
            mailMapper.addNewMailCheck(model);
        } catch (Exception e) {
            return ResultData.error("系统错误,请联系客服");
        }
        //拼接邮件内容与地址
        String url = HTTP_URL.replace("{userId}", userId.toString()).replace("{uuid}", uuid);
        //发送邮件
        try {
            MailSender.sendMail("邮箱验证", MAIL_CONTENT.replace("{url}", url), MailContentTypeEnum.TEXT, mail);
        } catch (Exception e) {
            return ResultData.error("系统错误,请联系客服");
        }
        return ResultData.success("邮箱激活地址已发送,请前往邮箱验证");
    }


    @Override
    public ResultData mailActive(Integer id, String uuid) {
        if (AssertUtil.assertObject(id)) {
            return ResultData.error("用户ID不能为空");
        } else if (AssertUtil.assertObject(uuid)) {
            return ResultData.error("验证码不能为空");
        }
        //验证
        TbMailModel mailByUserIdAndCode = mailMapper.findMailByUserIdAndCode(id, uuid);
        if (mailByUserIdAndCode != null) {
            //验证成功
            try {
                mailMapper.activeSuccess(id);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.error("邮件验证失败,请联系客服");
            }
            return ResultData.success("邮箱激活成功");
        }
        return ResultData.error("邮件验证失败,请联系客服");
    }
}
