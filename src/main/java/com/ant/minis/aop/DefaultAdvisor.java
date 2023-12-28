package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 20:08
 */
public class DefaultAdvisor implements Advisor {

    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {
    }


    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Advice getAdvice() {
        return this.methodInterceptor;
    }
}
