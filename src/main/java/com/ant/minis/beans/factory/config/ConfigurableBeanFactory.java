package com.ant.minis.beans.factory.config;

import com.ant.minis.beans.factory.BeanFactory;

/**
 * <p>
 * Configuration interface to be implemented by most bean factories.
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 11:29
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 新增一个在 Bean工厂创建 bean 过程中被执行的后置处理器。在工厂配置期间调用
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 返回已经注册了的 Bean 的数量
     *
     * @return
     */
    int getBeanPostProcessorCount();

    /**
     * Register a dependent bean for the given bean,
     * to be destroyed before the given bean is destroyed
     *
     * @param beanName
     * @param dependentBeanName
     */
    void registerDependentBean(String beanName, String dependentBeanName);

    /**
     * Return the names of all beans which depend on the specified bean, if any.
     *
     * @param beanName
     * @return
     */
    String[] getDependentBeans(String beanName);

    /**
     * Return the names of all beans that the specified bean depends on, if any.
     *
     * @param beanName
     * @return
     */
    String[] getDependenciesForBean(String beanName);
}
