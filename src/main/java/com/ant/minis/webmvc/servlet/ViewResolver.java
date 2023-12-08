package com.ant.minis.webmvc.servlet;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/4 15:35
 */
public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
