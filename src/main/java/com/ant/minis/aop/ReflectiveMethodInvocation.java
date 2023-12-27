package com.ant.minis.aop;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/26 20:18
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object proxy;
    protected final Object target;
    protected final Method method;
    protected Object[] arguments;
    private Class<?> targetClass;

    protected ReflectiveMethodInvocation(
            Object proxy, Object target, Method method,  Object[] arguments, Class<?> targetClass) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }

}
