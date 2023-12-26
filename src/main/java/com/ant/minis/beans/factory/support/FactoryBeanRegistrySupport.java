package com.ant.minis.beans.factory.support;

import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.FactoryBean;
import com.sun.istack.internal.Nullable;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/25 10:14
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * Determine the type for the given FactoryBean.
     * @param factoryBean the FactoryBean instance to check
     * @return the FactoryBean's object type,
     * or {@code null} if the type cannot be determined yet
     */
    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        try {
            return factoryBean.getObjectType();
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object = doGetObjectFromFactoryBean(factory, beanName);
        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * <p>
     * 从 factory bean 中获取内部包含的对象
     * </p>
     *
     * @param factory
     * @param beanName
     * @return java.lang.Object
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName) {
        Object object = null;
        try {
            object = factory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }
}
