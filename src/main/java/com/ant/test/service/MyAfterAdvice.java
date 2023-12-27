package com.ant.test.service;

import com.ant.minis.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/12/27 17:00
 */
public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("after........................");
    }
}
