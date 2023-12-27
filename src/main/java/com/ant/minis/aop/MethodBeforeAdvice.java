package com.ant.minis.aop;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 14:39
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
