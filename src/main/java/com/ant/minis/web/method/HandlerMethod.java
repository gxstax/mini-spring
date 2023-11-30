package com.ant.minis.web.method;

import com.sun.org.apache.bcel.internal.classfile.MethodParameter;

import java.lang.reflect.Method;

/**
 * <p>
 * 处理方法
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 14:35
 */
public class HandlerMethod {
    private Object bean;
    private Class<?> beanType;
    private Method method;
    private MethodParameter[] parameters;
    private Class<?> returnType;
    private String description;
    private String className;
    private String methodName;

    public HandlerMethod(Method method, Object obj) {
        this.setMethod(method);
        this.setBean(obj);
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
