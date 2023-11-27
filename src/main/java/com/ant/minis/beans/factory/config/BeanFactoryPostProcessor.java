package com.ant.minis.beans.factory.config;

import com.ant.minis.beans.BeansException;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 15:08
 */
public interface BeanFactoryPostProcessor {

    /**
     * Modify the application context's internal bean factory after its standard
     * initialization. All bean definitions will have been loaded, but no beans
     * will have been instantiated yet. This allows for overriding or adding
     * properties even to eager-initializing beans.
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException in case of errors
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
