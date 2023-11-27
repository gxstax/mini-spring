package com.ant.minis.web;

import com.ant.minis.core.io.Resource;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 18:52
 */
public class XmlConfigReader {
    public XmlConfigReader() { }
    public Map loadConfig(Resource res) {
        Map mappings = new HashMap<>();
        while (res.hasNext()) {
            //读所有的节点，解析id, class和value
            Element element = (Element)res.next();
            String beanID=element.attributeValue("id");
            String beanClassName=element.attributeValue("class");
            String beanMethod=element.attributeValue("value");
            mappings.put(beanID, new MappingValue(beanID,beanClassName,beanMethod));
        } return mappings;
    }
}
