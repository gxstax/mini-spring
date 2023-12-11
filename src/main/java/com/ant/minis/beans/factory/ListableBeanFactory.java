package com.ant.minis.beans.factory;

import com.ant.minis.beans.BeansException;

import java.util.Map;

/**
 * <p>
 * {@link BeanFactory} 接口扩展
 * </p>
 *
 * @author Ant
 * @since 2023/11/27 11:21
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * Check if this bean factory contains a bean definition with the given name.
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * Return the number of beans defined in the factory.
     *
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * Return the names of all beans defined in this factory.
     *
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     *
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     *
     * @param type
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

}
