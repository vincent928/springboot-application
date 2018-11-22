package com.moon.service.impl;

import com.moon.common.ResultData;
import com.moon.mapper.UserMapper;
import com.moon.model.User;
import com.moon.service.UserService;
import com.moon.util.AssertUtil;
import com.moon.util.MD5Util;
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
    public ResultData addUser(User user) {
        if (AssertUtil.assertObject(user.getUsername()) || AssertUtil.assertObject(user.getPassword())) {
            return ResultData.error("用户名或密码不能为空");
        }
        //密码加密
        try {
            user.setPassword(MD5Util.getMD5(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("系统错误，请联系客服");
        }
        int i = userMapper.insertUser(user);
        if (i > 0) {
            return ResultData.success("新增成功");
        }
        return ResultData.error("失败");
    }

    @Override
    public ResultData listUser(Map params) {
        List<User> users = userMapper.listUser(params);
        if (AssertUtil.assertObject(users)) {
            return ResultData.miss("查无数据");
        }
        return ResultData.success(users);
    }
}
