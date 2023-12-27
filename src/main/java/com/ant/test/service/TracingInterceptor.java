package com.ant.test.service;

import com.ant.minis.aop.MethodInterceptor;
import com.ant.minis.aop.MethodInvocation;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 19:50
 */
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("method " + invocation.getMethod() + " is called on "+
                invocation.getThis()+" with args " + invocation.getArguments());
        Object retVal = invocation.proceed();
        System.out.println("method " + invocation.getMethod()+" returns " + retVal);
        return retVal;
    }

}
