package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 15:00
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {

    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        advice.afterReturning(retVal, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return retVal;
    }
}
