package com.ant.minis.beans.factory;


import com.ant.minis.beans.BeanDefinition;
import com.ant.minis.beans.BeansException;

/**
 * <p>
 * Bean 工厂类
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 00:56
 **/
public interface BeanFactory {
    /**
     * <p>
     * 获取 bean 实例
     * </p>
     *
     * @param beanName
     * @return java.lang.Object
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * <p>
     * 容器中是否包含 Bean
     * </p>
     *
     * @param name
     * @return java.lang.Boolean
     */
    Boolean containsBean(String name);

    /**
     * <p>
     * 根据 BeanDefinition 注册实例到 Bean 工厂
     * </p>
     *
     * @param beanName  注册beanName
     * @param obj       注册类对象
     */
    void registerBean(String beanName, Object obj);

    /**
     * <p>
     * 判断指定的 beanName 的 Bean 是否是单例模式
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    boolean isSingleton(String beanName);

    /**
     * <p>
     * 判断指定 beanName 的 Bean 是否是原型模式
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    boolean isProtocol(String beanName);
}

