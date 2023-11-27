package com.ant.minis.beans.factory.support;


import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.beans.factory.SimpleBeanFactory;
import com.ant.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.ant.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.ant.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.ant.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.ant.minis.beans.factory.support.AbstractApplicationContext;
import com.ant.minis.beans.factory.support.DefaultListableBeanFactory;
import com.ant.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.ant.minis.context.*;
import com.ant.minis.core.io.ClassPathXmlResource;
import com.ant.minis.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * xml 解析类
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 00:34
 **/
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    DefaultListableBeanFactory beanFactory;

    private final List beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    /**
     * <p>
     * context 负责整合容器的启动过程，读取外部配置，解析 bean 定义
     * 创建 BeanFactory
     * </p>
     *
     * @param fileName
     * @return null
     */
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            try {
                refresh();
            } catch (Exception e) {

            }
        }
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) { }

    /**
     * <p>
     * 事件发布
     * </p>
     *
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }




    /**
     * <p>
     * 是否存在指定name的bean对象
     * </p>
     *
     * @param name  指定的beanName
     * @return 是否存在指定name的Bean
     */
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
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
        return false;
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
    public boolean isPrototype(String beanName) {
        return false;
    }

    /**
     * <p>
     * 获取bean的类型
     * </p>
     *
     * @param name
     * @return java.lang.Class<?>
     */
    @Override
    public Class<?> getType(String name) throws BeansException {
        return beanFactory.getType(name);
    }


}

