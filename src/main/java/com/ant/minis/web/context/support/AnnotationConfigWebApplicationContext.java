package com.ant.minis.web.context.support;

import com.ant.minis.beans.factory.support.ClassPathXmlApplicationContext;
import com.ant.minis.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/28 16:56
 */
public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext
        implements WebApplicationContext {

    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
