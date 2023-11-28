package com.ant.minis.beans.factory.annotation;


import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.ant.minis.beans.factory.config.BeanPostProcessor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <p>
 * 处理 {@link Autowired @Autowired} 注解的处理器
 * </p>
 *
 * @author Ant
 * @since 2023/4/5 23:41
 **/
//@Slf4j
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;

    /**
     * <p>
     * Bean 初始化之前
     * </p>
     *
     * @param bean
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (null != fields) {
            // 对每一个属性进行判断，如果带有 @Autowired 注解则进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    // 根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.beanFactory.getBean(fieldName);
                    // 设置属性值
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
//                        log.info("autowire {} for bean {}", fieldName, beanName);
                    } catch (Exception e) {}
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * Bean 初始化之后
     * </p>
     *
     * @param bean
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}

