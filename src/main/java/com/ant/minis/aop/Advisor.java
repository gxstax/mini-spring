package com.ant.minis.aop;

/**
 * <p>
 * 通知者
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 19:55
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();

    void setMethodInterceptor(MethodInterceptor methodInterceptor);

    Advice getAdvice();
}
