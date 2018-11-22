package com.moon.mapper;

import com.moon.model.User;

import java.util.List;
import java.util.Map;

/**
 * Author : moon
 * Date  : 2018/11/21 16:02
 * Description : Class for
 */
public interface UserMapper {

    List<User> listUser(Map params);


}
