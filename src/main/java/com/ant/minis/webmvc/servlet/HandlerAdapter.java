package com.ant.minis.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 14:43
 */
public interface HandlerAdapter {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
