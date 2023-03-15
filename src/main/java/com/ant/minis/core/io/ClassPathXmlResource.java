package com.ant.minis.core.io;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * <p>
 * ClassPath资源
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 01:02
 **/
public class ClassPathXmlResource implements Resource {

    Document document;
    Element rootElement;
    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);

        try {
            this.document = saxReader.read(xmlPath);
            this.rootElement = document.getRootElement();
            this.elementIterator = rootElement.elementIterator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}

