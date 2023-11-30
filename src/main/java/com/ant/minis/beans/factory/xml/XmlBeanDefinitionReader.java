package com.ant.minis.beans.factory.xml;


import com.ant.minis.beans.*;
import com.ant.minis.beans.factory.config.ConstructorArgumentValue;
import com.ant.minis.beans.factory.config.ConstructorArgumentValues;
import com.ant.minis.beans.factory.config.BeanDefinition;
import com.ant.minis.beans.factory.support.AbstractBeanFactory;
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
    private AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
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
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            //get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for (Element e : constructorElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                AVS.addArgumentValue(new ArgumentValue(pType, pName, pValue));
            }
            beanDefinition.setArgumentValues(AVS);
            //end of handle constructor

            //handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            //end of handle properties

            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}

