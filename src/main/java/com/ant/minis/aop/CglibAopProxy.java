package com.ant.minis.aop;

/**
 * <p>
 * 基于Cglib的动态代理
 * </p>
 *
 * @author Ant
 * @since 2024/1/2 15:52
 */
public class CglibAopProxy implements AopProxy {

    Object target;

    PointcutAdvisor advisor;

    public CglibAopProxy(Object target, PointcutAdvisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        // TODO
        return null;
    }

    @Override
    public Object getProxy(ClassLoader var1) {
        return null;
    }
}
