package com.moon.util.mail;

import com.moon.enums.mail.MailContentTypeEnum;
import com.moon.model.email.MailModel;
import com.moon.util.PropertiesUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Author : moon
 * Date  : 2018/11/21 13:40
 * Description : Class for 邮件工具类
 */
public class MailSender {

    //邮件实体
    private static MailModel mail = new MailModel();
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_SERVICE = "mail.smtp.service";

    private static final String MAIL_FROM_ADDRESS = "mail.from.address";
    private static final String MAIL_FROM_SMTP_PWD = "mail.from.smtp.pwd";
    private static final String MAIL_FROM_NICKNAME = "mail.from.nickname";
    private static final String MAIL_USER = "mail.user";
    private static final String MAIL_PASSWORD = "mail.password";


    /**
     * 发送邮件工具
     *
     * @param title
     * @param content
     * @param typeEnum
     * @param targets
     */
    public static void sendMail(String title, String content, MailContentTypeEnum typeEnum, List<String> targets) throws Exception {
        new MailSender().title(title)
                .content(content)
                .contentType(typeEnum)
                .target(targets)
                .send();
    }

    /**
     * 发送邮件工具类,默认为html内容
     *
     * @param title
     * @param content
     * @param targets
     * @throws Exception
     */
    public static void sendMail(String title, String content, List<String> targets) throws Exception {
        new MailSender().title(title)
                .target(targets)
                .contentType(MailContentTypeEnum.HTML)
                .content(content)
                .send();
    }


    /**
     * 发送单目标邮件
     *
     * @param title
     * @param content
     * @param target
     */
    public static void sendMail(String title, String content, MailContentTypeEnum typeEnum, String target) throws Exception {
        new MailSender().title(title)
                .content(content)
                .contentType(typeEnum)
                .target(new ArrayList<String>() {{
                    add(target);
                }})
                .send();
    }


    /**
     * 发送单目标邮件,默认为html
     *
     * @param title
     * @param content
     * @param target
     */
    public static void sendMail(String title, String content, String target) throws Exception {
        new MailSender().title(title)
                .content(content)
                .contentType(MailContentTypeEnum.HTML)
                .target(new ArrayList<String>() {{
                    add(target);
                }})
                .send();
    }


    /**
     * 设置邮件标题
     *
     * @param title 标题
     * @return
     */
    public MailSender title(String title) {
        mail.setTitle(title);
        return this;
    }

    /**
     * 设置邮件内容
     *
     * @param content 内容
     * @return
     */
    public MailSender content(String content) {
        mail.setContent(content);
        return this;
    }

    /**
     * 设置邮件类型
     *
     * @param typeEnum
     * @return
     */
    public MailSender contentType(MailContentTypeEnum typeEnum) {
        mail.setContentType(typeEnum.getValue());
        return this;
    }

    /**
     * 设置邮件目标地址集合
     *
     * @param target 邮件地址集合
     * @return
     */
    public MailSender target(List<String> target) {
        mail.setList(target);
        return this;
    }


    /**
     * 邮件发送
     *
     * @throws Exception
     */
    public void send() throws Exception {
        verifyMail(mail);
        sendMail();
    }

    private void sendMail() throws Exception {
        // 读取/resources/mail_zh_CN.properties文件内容
        final PropertiesUtil properties = new PropertiesUtil("mail");
        /**--------------------创建Properties类用于记录邮箱属性-----------------------------*/
        final Properties props = new Properties();
        // smtp发送邮件，必须进行身份验证
        props.put(MAIL_SMTP_AUTH, "true");
        // smtp服务器
        props.put(MAIL_SMTP_HOST, properties.getValue(MAIL_SMTP_SERVICE));
        // 设置端口号,QQ邮箱端口号465/587
        props.put(MAIL_SMTP_PORT, properties.getValue(MAIL_SMTP_PORT));
        //设置发送邮箱
        props.put(MAIL_USER, properties.getValue(MAIL_FROM_ADDRESS));
        //设置发送邮箱的16位SMTP身份验证
        props.put(MAIL_PASSWORD, properties.getValue(MAIL_FROM_SMTP_PWD));

        /**--------------------构建授权信息，用于smtp身份验证-------------------------------*/
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = props.getProperty(MAIL_USER);
                String password = props.getProperty(MAIL_PASSWORD);
                return new PasswordAuthentication(username, password);
            }
        };
        /**---------------------使用环境属性和授权信息，创建邮件会话-------------------------*/
        Session mailSession = Session.getInstance(props, authenticator);
        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        //设置发件人
        String nickName = MimeUtility.encodeText(properties.getValue(MAIL_FROM_NICKNAME));
        InternetAddress from = new InternetAddress(nickName + "<" + props.getProperty(MAIL_USER) + ">");
        message.setFrom(from);

        //设置邮件标题
        message.setSubject(mail.getTitle());
        //根据邮件类型发送邮件
        setMailType(message);
        //发送邮件
        List<String> targetList = mail.getList();
        targetList.forEach((target) -> {
            try {
                //设置收件人邮箱
                InternetAddress to = new InternetAddress(target);
                message.setRecipient(Message.RecipientType.TO, to);
                //发送邮件
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 邮件实体校验
     *
     * @param mail
     * @throws Exception
     */
    private void verifyMail(MailModel mail) throws Exception {
        //默认使用html类型
        if (mail.getContentType() == null) {
            mail.setContentType(MailContentTypeEnum.HTML.getValue());
        }
        if (mail.getTitle() == null || mail.getTitle().trim().length() == 0) {
            throw new Exception("尚未设置邮件标题,请重新设置邮件标题");
        }
        if (mail.getContent() == null || mail.getContent().trim().length() == 0) {
            throw new Exception("尚未设置邮件内容,请重新设置邮件内容");
        }
        if (mail.getList() == null || mail.getList().size() < 1) {
            throw new Exception("尚未设置目标邮箱地址,请重新设置邮箱地址");
        }
    }

    /**
     * 设置邮件内容类型 html/text
     *
     * @param message
     * @throws MessagingException
     */
    private void setMailType(MimeMessage message) throws MessagingException {
        //html类型
        if (mail.getContentType().equals(MailContentTypeEnum.HTML.getValue())) {
            //设置邮件内容体
            message.setContent(mail.getContent(), mail.getContentType());
        }
        //文本类型
        if (mail.getContentType().equals(MailContentTypeEnum.TEXT.getValue())) {
            message.setText(mail.getContent());
        }
    }


}
