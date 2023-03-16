package com.ant.minis.beans.factory.config;

import com.sun.istack.internal.Nullable;

/**
 * <p>
 * 单例 bean 注册器
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 23:15
 **/
public interface SingletonBeanRegistry {

    /**
     * <p>
     *
     * </p>
     *
     * @param beanName          bean名称
     * @param singletonObject   存在的单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * <p>
     * 返回指定名称的单例 bean
     * </p>
     *
     * @param beanName  要查找的bean的名称
     * @return 已注册的单例对象, 如果未找到返回 null
     */
    Object getSingleton(String beanName);

    /**
     * <p>
     * 判断是否存在该单例bean
     * </p>
     *
     * @param beanName  要查找的bean的名称
     * @return bean工厂内是否存在该指定名称的单例bean
     */
    boolean containsSingleton(String beanName);

    /**
     * <p>
     * 返回所有单例bean的名称
     * </p>
     *
     * @return 单例bean名称数组
     */
    String[] getSingletonNames();

}
