package com.ant.minis.beans;


/**
 * <p>
 * Bean定义类
 * </p>
 *
 * @author Ant
 * @since 2023/3/15 23:07
 **/
public class BeanDefinition {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    private boolean lazyInit = false;

    private String[] dependsOn;

    private ArgumentValues constructArgumentValues;
    private PropertyValues propertyValues;
    // 初始化方法名
    private String initMethodName;

    private volatile Object beanClass;

    /**
     * bean id 容器中标识实例
     */
    private String id;

    /**
     * bean className 反射使用的类的全类名
     */
    private String className;

    private String scope = SCOPE_SINGLETON;

    public boolean isSingleton() {
        return this.scope.equals(SCOPE_SINGLETON);
    }

    public boolean isPrototype() {
        return this.scope.equals(SCOPE_PROTOTYPE);
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public ArgumentValues getConstructArgumentValues() {
        return constructArgumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public String getScope() {
        return scope;
    }

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

