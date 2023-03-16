package com.ant.minis.beans.factory.support;


import com.ant.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  默认的单例bean注册器
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 23:26
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 容器中存放的所有bean的名称列表
    protected List<String> beanNames = new ArrayList<>();

    // 容器中存放的所有bean实例的map
    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    /**
     * <p>
     *
     * </p>
     *
     * @param beanName        bean名称
     * @param singletonObject 存在的单例对象
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    /**
     * <p>
     * 返回指定名称的单例 bean
     * </p>
     *
     * @param beanName 要查找的bean的名称
     * @return 已注册的单例对象, 如果未找到返回 null
     */
    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    /**
     * <p>
     * 判断是否存在该单例bean
     * </p>
     *
     * @param beanName 要查找的bean的名称
     * @return bean工厂内是否存在该指定名称的单例bean
     */
    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    /**
     * <p>
     * 返回所有单例bean的名称
     * </p>
     *
     * @return 单例bean名称数组
     */
    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.singletons.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }
}

