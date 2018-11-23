package com.moon.service.mail;


import com.moon.common.ResultData;

public interface MailService {

    ResultData getMailCheckAddress(Integer userId,String mail);
    ResultData mailActive(Integer id,String uuid);
}
