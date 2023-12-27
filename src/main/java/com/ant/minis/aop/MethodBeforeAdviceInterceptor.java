package com.ant.minis.aop;

/**
 * <p>
 * 方法执行前通知拦截器
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 14:48
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {

    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
