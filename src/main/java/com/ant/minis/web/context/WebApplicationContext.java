package com.ant.minis.web.context;

import com.ant.minis.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/28 16:47
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);
}
