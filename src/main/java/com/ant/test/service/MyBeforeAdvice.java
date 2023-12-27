package com.ant.test.service;

import com.ant.minis.aop.BeforeAdvice;
import com.ant.minis.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 16:59
 */
public class MyBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before................");
    }
}
