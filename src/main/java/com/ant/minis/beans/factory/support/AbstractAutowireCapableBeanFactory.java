package com.ant.minis.beans.factory.support;


import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.ant.minis.beans.factory.config.AutowireCapableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/4/17 01:07
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * <p>
     * bean 初始化前的一些处理回调
     * </p>
     *
     * @param existingBean
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (null != result) {
                return result;
            }
        }
        return result;
    }

    /**
     * <p>
     * bean 初始化后的一些处理回调
     * </p>
     *
     * @param existingBean
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : beanPostProcessors) {
            result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (null != result) {
                return result;
            }
        }
        return result;
    }
}

