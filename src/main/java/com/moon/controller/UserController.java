package com.moon.controller;

import com.moon.common.ResultData;
import com.moon.model.User;
import com.moon.service.UserService;
import com.moon.service.mail.MailService;
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
    @Resource
    private MailService mailService;

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

    /**
     * 获取mail验证地址
     *
     * @param mail
     * @return
     */
    @RequestMapping(value = "/mail/check/address", method = RequestMethod.POST)
    public ResultData mailCheck(@RequestParam Integer id, @RequestParam String mail) {
        return mailService.getMailCheckAddress(id, mail);
    }

    /**
     * 邮箱验证
     *
     * @param id
     * @param code
     * @return
     */
    @RequestMapping(value = "/mail/check/active", method = RequestMethod.GET)
    public ResultData mailActive(@RequestParam("id") Integer id, @RequestParam("code") String code) {
        return mailService.mailActive(id, code);
    }


    /**
     * ---------------------------------------单点登录----------------------------------------------
     */

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultData login(String username, String password) {
        return userService.login(username, password);
    }

    /**
     * Token检查
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/token/check", method = RequestMethod.POST)
    public ResultData checkToken(@RequestParam(value = "token") String token) {
        return userService.checkToken(token);
    }

}
