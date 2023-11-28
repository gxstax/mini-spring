package com.ant.test;

import com.ant.minis.web.RequestMapping;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 19:01
 */
public class HelloWorldBean {

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
}
