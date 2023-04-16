package com.ant.minis.beans.factory.xml;


import com.ant.minis.beans.*;
import com.ant.minis.beans.factory.SimpleBeanFactory;
import com.ant.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.ant.minis.beans.factory.config.ConstructorArgumentValue;
import com.ant.minis.beans.factory.config.ConstructorArgumentValues;
import com.ant.minis.beans.factory.config.BeanDefinition;
import com.ant.minis.core.io.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * XML bean 定义类读取
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 01:08
 **/
public class XmlBeanDefinitionReader {
    private AutowireCapableBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(AutowireCapableBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
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

            List<String> refs = new ArrayList<>();
            PropertyValues PVS = new PropertyValues();
            List<Element> propertyElements = element.elements("property");
            for (Element propertyElement : propertyElements) {
                String type = propertyElement.attributeValue("type");
                String name = propertyElement.attributeValue("name");
                String pValue = propertyElement.attributeValue("value");
                String pRef = propertyElement.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (null != pValue && !pValue.isEmpty()) {
                    pV = pValue;
                } else if (null != pRef) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(type, name, pV, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            // 处理构造器函数
            int i = 0;
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            List<Element> constructorElements = element.elements("constructor-arg");
            for (Element constructorElement : constructorElements) {
                String type = constructorElement.attributeValue("type");
                String name = constructorElement.attributeValue("name");
                String value = constructorElement.attributeValue("value");
                ConstructorArgumentValue constructorArgumentValue = new ConstructorArgumentValue(value, type, name);
                AVS.addGenericArgumentValue(constructorArgumentValue);
                AVS.addIndexedArgumentValues(i++, constructorArgumentValue);
            }
            beanDefinition.setConstructorArgumentValues(AVS);

            this.simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}

