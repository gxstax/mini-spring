package com.ant.minis.web;

import com.ant.minis.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 18:54
 */
public class ClassPathXmlResource implements Resource {

    Document document;
    Element rootElement;
    Iterator elementIterator;

    public ClassPathXmlResource(URL xmlPath) {
        SAXReader saxReader=new SAXReader();
        try {
            this.document = saxReader.read(xmlPath);
            this.rootElement=document.getRootElement();
            this.elementIterator=this.rootElement.elementIterator();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
