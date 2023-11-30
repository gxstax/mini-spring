package com.ant.minis.beans.factory.support;

import com.ant.minis.beans.factory.config.BeanDefinition;

/**
 * <p>
 * BeanDefinition 定义接口
 * </p>
 *
 * @author Ant
 * @since 2023/3/21 12:39
 **/
public interface BeanDefinitionRegistry {

    /**
     * <p>
     * 注册BeanDefinition
     * </p>
     *
     * @param beanName
     * @param bd
     */
    void registerBeanDefinition(String beanName, BeanDefinition bd);

    /**
     * <p>
     * 移除指定 beanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     */
    void removeBeanDefinition(String beanName);

    /**
     * <p>
     * 返回一个指定 BeanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     * @return com.ant.minis.beans.factory.config.BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * <p>
     * 查找是否有指定 beanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    boolean containsBeanDefinition(String beanName);

}
