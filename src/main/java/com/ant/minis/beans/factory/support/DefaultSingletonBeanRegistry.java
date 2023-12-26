package com.ant.minis.beans.factory.support;


import com.ant.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
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
    protected Map<String, Object> singletonObjects =new ConcurrentHashMap<>(256);
    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    protected Map<String,Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

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
        synchronized(this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }

            this.singletonObjects.put(beanName, singletonObject);
            this.beanNames.add(beanName);
            System.out.println(" bean registerded............. " + beanName);
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
        return this.singletonObjects.get(beanName);
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
        return this.singletonObjects.containsKey(beanName);
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
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }

    public void registerDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
            return;
        }

        // No entry yet -> fully synchronized manipulation of the dependentBeans Set
        synchronized (this.dependentBeanMap) {
            dependentBeans = this.dependentBeanMap.get(beanName);
            if (dependentBeans == null) {
                dependentBeans = new LinkedHashSet<String>(8);
                this.dependentBeanMap.put(beanName, dependentBeans);
            }
            dependentBeans.add(dependentBeanName);
        }
        synchronized (this.dependenciesForBeanMap) {
            Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
            if (dependenciesForBean == null) {
                dependenciesForBean = new LinkedHashSet<String>(8);
                this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
            }
            dependenciesForBean.add(beanName);
        }

    }

    public boolean hasDependentBean(String beanName) {
        return this.dependentBeanMap.containsKey(beanName);
    }

    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (dependentBeans == null) {
            return new String[0];
        }
        return (String[]) dependentBeans.toArray();
    }

    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (dependenciesForBean == null) {
            return new String[0];
        }
        return (String[]) dependenciesForBean.toArray();

    }
}

