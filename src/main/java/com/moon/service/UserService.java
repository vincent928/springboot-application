package com.moon.service;

import com.moon.common.ResultData;
import com.moon.model.User;

import java.util.Map;

/**
 * Author : CaoXm
 * Date  : 2018/11/21 16:50
 * Description : Interface for
 */
public interface UserService {


    ResultData listUser(Map params);

    ResultData addUser(User user);

    ResultData login(String username, String password);

    ResultData checkToken(String token);
}
