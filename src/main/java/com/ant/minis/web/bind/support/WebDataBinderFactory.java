package com.ant.minis.web.bind.support;

import com.ant.minis.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/1 9:51
 */
public class WebDataBinderFactory {
    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder wbd = new WebDataBinder(target, objectName);
        initBinder(wbd, request);
        return wbd;
    }

    protected void initBinder(WebDataBinder dataBinder, HttpServletRequest request) {

    }
}
