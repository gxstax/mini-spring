package com.ant.minis.beans.factory.xml;


import com.ant.minis.beans.BeanDefinition;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.core.io.Resource;
import org.dom4j.Element;

/**
 * <p>
 * XML bean 定义类读取
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 01:08
 **/
public class XmlBeanDefinitionReader {
    private BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * <p>
     * 加载 BeanDefinitions 到容器
     * </p>
     *
     * @param resource
     */
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}

