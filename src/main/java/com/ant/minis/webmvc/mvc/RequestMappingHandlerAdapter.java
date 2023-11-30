package com.ant.minis.webmvc.mvc;

import com.ant.minis.web.RequestMapping;
import com.ant.minis.web.context.WebApplicationContext;
import com.ant.minis.web.method.HandlerMethod;
import com.ant.minis.webmvc.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * <p>
 * 用来支持 {@link RequestMapping} 注解
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 14:28
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

    WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object obj = handler.getBean();

        Object objResult = null;
        try {
            objResult = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().append(objResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
