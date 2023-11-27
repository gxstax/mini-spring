package com.ant.minis.beans.factory.config;

import com.ant.minis.beans.factory.ListableBeanFactory;

/**
 * <p>
 * Configuration interface to be implemented by most listable bean factories.
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 13:36
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
