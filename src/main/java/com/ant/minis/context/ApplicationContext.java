package com.ant.minis.context;

import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.ListableBeanFactory;
import com.ant.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.ant.minis.beans.factory.config.ConfigurableBeanFactory;
import com.ant.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.ant.minis.core.env.Environment;
import com.ant.minis.core.env.EnvironmentCapable;

/**
 * <p>
 * Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 * </p>
 *
 * @author Ant
 * @since 2023/11/23 17:19
 */
public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    /**
     * Return a name for the deployed application that this context belongs to.
     * @return a name for the deployed application, or the empty String by default
     *
     * @return
     */
    String getApplicationName();

    /**
     * Return the timestamp when this context was first loaded.
     *
     * @return the timestamp (ms) when this context was first loaded
     */
    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    /**
     * Return the {@link Environment} associated with this component.
     */
    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
