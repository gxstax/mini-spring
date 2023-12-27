package com.ant.minis.aop;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 14:40
 */
public interface AfterReturningAdvice extends AfterAdvice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
