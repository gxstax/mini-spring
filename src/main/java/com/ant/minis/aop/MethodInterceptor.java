package com.ant.minis.aop;

/**
 * <p>
 * 实现某个方法的拦截操作
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 18:04
 */
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
