package com.moon.service.impl;

import com.moon.common.ResultData;
import com.moon.mapper.UserMapper;
import com.moon.model.User;
import com.moon.service.UserService;
import com.moon.util.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author : moon
 * Date  : 2018/11/21 16:51
 * Description : Class for
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResultData listUser(Map params) {
        List<User> users = userMapper.listUser(params);
        if (AssertUtil.assertObject(users)) {
            return ResultData.miss("查无数据");
        }
        return ResultData.success(users);
    }
}
