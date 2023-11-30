package com.ant.minis.web.context.support;

import com.ant.minis.beans.factory.support.ClassPathXmlApplicationContext;
import com.ant.minis.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 * 来自 XML文档配置上下文
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 10:42
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext
        implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
