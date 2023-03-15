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
     * 根据 BeanDefinition 注册实例到 Bean 工厂
     * </p>
     *
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}

