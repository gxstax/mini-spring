package com.ant.minis.aop.framework.autoproxy;

import com.ant.minis.aop.AopProxyFactory;
import com.ant.minis.aop.DefaultAopProxyFactory;
import com.ant.minis.aop.PointcutAdvisor;
import com.ant.minis.aop.ProxyFactoryBean;
import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.beans.factory.config.BeanPostProcessor;
import com.ant.minis.util.PatternMatchUtils;

/**
 * <p>
 * 动态代理自动创建
 * </p>
 *
 * @author Ant
 * @since 2023/12/29 14:57
 */
public class BeanNameAutoProxyCreator implements BeanPostProcessor {
    /** 代理对象名称模式 */
    String pattern;

    private BeanFactory beanFactory;

    private AopProxyFactory aopProxyFactory;

    /** 代理对象通知 beanName */
    private String interceptorName;

    /** 通知 */
    private PointcutAdvisor advisor;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

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
        if (isMatch(beanName, this.pattern)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setInterceptorName(this.interceptorName);
            proxyFactoryBean.setAopProxyFactory(this.aopProxyFactory);
            return proxyFactoryBean;
        }

        return bean;
    }

    protected boolean isMatch(String beanName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public PointcutAdvisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }
}
