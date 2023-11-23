package com.ant.minis.beans.factory.config;

import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.BeanFactory;

/**
 * <p>
 * 实现工厂中有自动注入能力的bean实现
 * </p>
 *
 * @author Ant
 * @since 2023/4/6 00:26
 **/
public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}

