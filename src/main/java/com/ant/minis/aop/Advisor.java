package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 19:55
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();

    void setMethodInterceptor(MethodInterceptor methodInterceptor);
}
