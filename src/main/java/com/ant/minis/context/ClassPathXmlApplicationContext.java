package com.ant.minis.context;


import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.beans.factory.SimpleBeanFactory;
import com.ant.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.ant.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.ant.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.ant.minis.core.io.ClassPathXmlResource;
import com.ant.minis.core.io.Resource;

/**
 * <p>
 * xml 解析类
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 00:34
 **/
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    private AutowireCapableBeanFactory beanFactory;

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
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (!isRefresh) {
            this.beanFactory.refresh();
        }
    }

    public void refresh() {
        registerBeanPostProcessors(this.beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

    /**
     * <p>
     * 从容器中获取 bean 实例
     * </p>
     *
     * @param beanName
     * @return java.lang.Object
     */
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
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
     * 注册bean
     * </p>
     *
     * @param beanName  指定要注册的bean名称
     * @param obj       要注册仅容器的bean
     */
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
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
        return null;
    }

    /**
     * <p>
     * 事件发布
     * </p>
     *
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}

