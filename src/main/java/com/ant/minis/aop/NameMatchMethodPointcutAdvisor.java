package com.ant.minis.aop;

import com.ant.minis.beans.BeansException;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 18:25
 */
public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor {

    private Advice advice = null;

    private MethodInterceptor methodInterceptor;

    private String mappedName;

    private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

    public NameMatchMethodPointcutAdvisor() {}

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
        MethodInterceptor mi = null;

        if (advice instanceof BeforeAdvice) {
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice)advice);
        } else if (advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice)advice);
        } else if (advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor)advice;
        }

        setMethodInterceptor(mi);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
        this.pointcut.setMappedName(mappedName);
    }

    public String getMappedName() {
        return mappedName;
    }
}
