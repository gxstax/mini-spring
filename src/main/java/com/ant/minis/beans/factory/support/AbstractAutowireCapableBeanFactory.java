package com.ant.minis.beans.factory.support;


import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.ant.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.ant.minis.beans.factory.config.BeanPostProcessor;

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
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
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
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
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
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (null != result) {
                return result;
            }
        }
        return result;
    }
}

