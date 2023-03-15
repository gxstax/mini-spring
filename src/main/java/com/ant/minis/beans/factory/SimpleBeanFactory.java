package com.ant.minis.beans.factory;


import com.ant.minis.beans.BeanDefinition;
import com.ant.minis.beans.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 简单 Bean 工厂
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 01:13
 **/
public class SimpleBeanFactory implements BeanFactory {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

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
        // 尝试哪去Bean
        Object singleton = this.singletons.get(beanName);
        // 如果此时还没有这个 Bean 的实例， 则获取它的定义来创建实例
        if (null == singleton) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeansException("Bean is not found");
            }
            BeanDefinition beanDefinition = beanDefinitions.get(i);

            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            singletons.put(beanDefinition.getId(), singleton);
        }
        return singleton;
    }

    /**
     * <p>
     * 根据 BeanDefinition 注册实例到 Bean 工厂
     * </p>
     *
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}

