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


    @Override
    public ResultData login(String username, String password) {
        //正常登录
        //登录成功以后 生成token
        //将Token设置为key,对应的value就是用户信息
        return null;
    }

    @Override
    public ResultData checkToken(String token) {
        //从redis中获取token对应的用户信息
        //没有就返回登录失败,有就返回用户信息
        return null;
    }
}
