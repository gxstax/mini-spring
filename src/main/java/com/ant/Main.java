package com.ant;

import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.support.ClassPathXmlApplicationContext;
import com.ant.test.AService;

public class Main {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml", false);

        AService aservice = (AService) context.getBean("aService");
        aservice.sayHello();
    }
}