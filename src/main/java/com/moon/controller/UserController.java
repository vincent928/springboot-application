package com.moon.controller;

import com.moon.common.ResultData;
import com.moon.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 查询用户列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ResultData showUserList(Map params) {
        return userService.listUser(params);
    }


}
