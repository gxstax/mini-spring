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
    /**
     * bean id 容器中标识实例
     */
    private String id;

    /**
     * bean className 反射使用的类的全类名
     */
    private String className;

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

