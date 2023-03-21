package com.ant.minis.beans.factory;


import com.ant.minis.beans.BeanDefinition;
import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.support.BeanDefinitionRegistry;
import com.ant.minis.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 简单 Bean 工厂
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 01:13
 **/
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

   private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

   private List<String> beanDefinitionNames = new ArrayList<>();

   public SimpleBeanFactory() {

   }

    /**
     * <p>
     * 注册BeanDefinition
     * </p>
     *
     * @param beanName
     * @param bd
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanName, bd);
        this.beanDefinitionNames.add(beanName);
        if (!bd.isLazyInit()) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * <p>
     * 移除指定 beanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     */
    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitionMap.remove(beanName);
        this.beanDefinitionNames.remove(beanName);
        this.removeSingleton(beanName);
    }

    /**
     * <p>
     * 返回一个指定 BeanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     * @return com.ant.minis.beans.BeanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    /**
     * <p>
     * 查找是否有指定 beanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    /**
     * <p>
     * 获取 bean 实例
     * </p>
     *
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试直接拿Bean
        Object singleton = this.getSingleton(beanName);
        // 如果此时还没有这个 Bean 的实例， 则获取它的定义来创建实例
        if (null == singleton) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (null == beanDefinition) {
                throw new BeansException("Bean is not found");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.registerBean(beanName, singleton);
        }
        return singleton;
    }

    /**
     * <p>
     * 注册bean
     * </p>
     *
     * @param beanDefinition
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    /**
     * <p>
     * 容器中是否包含 Bean
     * </p>
     *
     * @param beanName  指定的bean名称
     * @return 是否包含指定名称的bean
     */
    @Override
    public Boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    /**
     * <p>
     * 根据 BeanDefinition 注册实例到 Bean 工厂
     * </p>
     *
     * @param beanName 注册beanName
     * @param obj      注册类对象
     */
    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    /**
     * <p>
     * 判断指定的 beanName 的 Bean 是否是单例模式
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    /**
     * <p>
     * 判断指定 beanName 的 Bean 是否是原型模式
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    @Override
    public boolean isProtocol(String beanName) {
        return this.beanDefinitionMap.get(beanName).isPrototype();
    }


}

