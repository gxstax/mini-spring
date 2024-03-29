package com.ant.test.controller;

import com.ant.minis.beans.factory.annotation.Autowired;
import com.ant.minis.jdbc.core.JdbcTemplate;
import com.ant.minis.web.bind.annotation.RequestMapping;
import com.ant.minis.web.bind.annotation.ResponseBody;
import com.ant.test.entity.User;
import com.ant.test.proxy.DynamicProxy;
import com.ant.test.proxy.IAction;
import com.ant.test.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/27 19:01
 */
public class HelloWorldBean {

    @Autowired
    private UserService userService;

    @Autowired
    private IAction action;

    @Autowired
    private IAction action2;

    @RequestMapping("/test")
    public String doTest() {
        return "hello world for doTest!";
    }

    public String doGet() {
        return "hello world for doGet!";
    }

    public String doPost() {
        return "hello world for doPost!";
    }

    @RequestMapping("/test7")
    @ResponseBody
    public User doTest7(User user) {
        User userInfo = userService.getUserInfo(1);
        user.setName(user.getName() + "---");
        user.setBirthday(new Date());
        return userInfo;
    }

    @RequestMapping("/testaop")
    public void doTestAop(HttpServletRequest request, HttpServletResponse response) {
        action.doAction();
    }

    @RequestMapping("/testaop2")
    public void doTestAop2(HttpServletRequest request, HttpServletResponse response) {
        action.doSomething();
    }

    @RequestMapping("/testaop3")
    public void doTestAop3(HttpServletRequest request, HttpServletResponse response) {
        action2.doAction();
    }

    @RequestMapping("/testaop4")
    public void doTestAop4(HttpServletRequest request, HttpServletResponse response) {
        action2.doSomething();
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
