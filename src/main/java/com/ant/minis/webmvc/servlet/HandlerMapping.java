package com.ant.minis.webmvc.servlet;

import com.ant.minis.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 14:31
 */
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
