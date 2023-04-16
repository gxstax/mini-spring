package com.ant.minis.beans.factory.config;

import com.ant.minis.beans.BeansException;

/**
 * <p>
 * 用户自定义修改实例bean的工厂回调接口
 * </p>
 *
 * @author Ant
 * @since 2023/4/5 23:31
 **/
public interface BeanPostProcessor {

    /**
     * <p>
     * Bean 初始化之前
     * </p>
     *
     * @param bean
     * @param beanName
     * @return java.lang.Object
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * <p>
     * Bean 初始化之后
     * </p>
     *
     * @param bean
     * @param beanName
     * @return java.lang.Object
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
