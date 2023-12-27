package com.ant.minis.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * 方法调用
 * 描述对反射方法进行包装的接口
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 19:47
 */
public interface MethodInvocation {
    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed() throws Throwable;
}
