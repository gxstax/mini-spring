package com.ant.minis.aop;

import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.beans.factory.BeanFactoryAware;
import com.ant.minis.beans.factory.FactoryBean;
import com.ant.minis.util.ClassUtils;

/**
 * <p>
 * 代理工厂Bean
 * </p>
 *
 * @author Ant
 * @since 2023/12/25 10:43
 */
public class ProxyFactoryBean implements FactoryBean<Object>, BeanFactoryAware {
    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;

    private BeanFactory beanFactory;

    private String interceptorName;

    private Advisor advisor;

    private synchronized void initializeAdvisor() {
        Object advice;
        MethodInterceptor mi = null;

        try {
            advice = this.beanFactory.getBean(this.interceptorName);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

        if (advice instanceof BeforeAdvice) {
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice)advice);
        } else if (advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice)advice);
        } else if (advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor)advice;
        }

        advisor = new DefaultAdvisor();
        advisor.setMethodInterceptor(mi);
    }

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public ProxyFactoryBean(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target, this.advisor);
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * <p>
     * Bean的声明周期构实例化时会使用该方法来实例化具体的代理对象
     * </p>
     *
     * @return java.lang.Object
     */
    @Override
    public Object getObject() throws Exception {
        initializeAdvisor();
        return getSingletonInstance();
    }

    private synchronized Object getSingletonInstance() {
        // 获取代理
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    protected Object getProxy(AopProxy aopProxy) {
        //生成代理对象
        return aopProxy.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    /**
     * <p>
     * 供bean实例化的时候回调，用来注入BeanFactory
     * </p>
     *
     * @param beanFactory
     * @return void
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
