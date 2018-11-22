package com.moon.controller;

import com.moon.common.ResultData;
import com.moon.model.User;
import com.moon.service.UserService;
import com.moon.service.phone.PhoneService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Author : moon
 * Date  : 2018/11/21 16:50
 * Description : Class for
 */
@RestController
@RequestMapping("/user")
public class UserController extends BasicController {

    @Resource
    private UserService userService;
    @Resource
    private PhoneService phoneService;

    /**
     * 查询用户列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ResultData showUserList(Map params) {
        return userService.listUser(params);
    }

    /**
     * 注册新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultData addNewUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 获取短信验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/phone/getcode", method = RequestMethod.POST)
    public ResultData phoneCheck(@RequestParam String phone) {
        return phoneService.getPhoneCode(phone);
    }


}
