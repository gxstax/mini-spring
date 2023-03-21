package com.ant;

import com.ant.minis.beans.BeansException;
import com.ant.minis.context.ClassPathXmlApplicationContext;
import com.ant.test.DemoService;

public class Main {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        DemoService aservice = (DemoService) context.getBean("aservice");
        aservice.sayHello();
    }
}