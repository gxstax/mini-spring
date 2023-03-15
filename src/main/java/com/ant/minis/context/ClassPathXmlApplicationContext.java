package com.ant.minis.context;


import com.ant.minis.beans.BeanDefinition;
import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.beans.factory.SimpleBeanFactory;
import com.ant.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.ant.minis.core.io.ClassPathXmlResource;
import com.ant.minis.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.net.URL;
import java.util.*;

/**
 * <p>
 * xml 解析类
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 00:34
 **/
public class ClassPathXmlApplicationContext {
    private BeanFactory beanFactory;

    /**
     * <p>
     * context 负责整合容器的启动过程，读取外部配置，解析 bean 定义
     * 创建 BeanFactory
     * </p>
     *
     * @param fileName
     * @return null
     */
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
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
     * 注册bean
     * </p>
     *
     * @param beanDefinition
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

}

