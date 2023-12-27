package com.ant.minis.beans.factory;

import com.ant.minis.beans.BeansException;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/12/27 12:11
 */
public interface BeanFactoryAware {

    /**
     * <p>
     * 供bean实例化的时候回调，用来注入BeanFactory
     * </p>
     *
     * @param beanFactory
     * @return void
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
