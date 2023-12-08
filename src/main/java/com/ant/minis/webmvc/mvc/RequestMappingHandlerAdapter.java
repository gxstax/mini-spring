package com.ant.minis.webmvc.mvc;

import com.ant.minis.beans.BeansException;
import com.ant.minis.web.bind.annotation.RequestMapping;
import com.ant.minis.web.bind.WebDataBinder;
import com.ant.minis.web.bind.annotation.ResponseBody;
import com.ant.minis.web.bind.support.WebBindingInitializer;
import com.ant.minis.web.bind.support.WebDataBinderFactory;
import com.ant.minis.web.context.WebApplicationContext;
import com.ant.minis.web.converter.HttpMessageConverter;
import com.ant.minis.web.method.HandlerMethod;
import com.ant.minis.webmvc.servlet.HandlerAdapter;
import com.ant.minis.webmvc.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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

    WebBindingInitializer webBindingInitializer;
    private HttpMessageConverter messageConverter = null;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        try {
            invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response,
                                               HandlerMethod handlerMethod) throws Exception {
        ModelAndView mav = null;



        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();

        Object[] methodParamObjs = new Object[methodParameters.length];
        int i = 0;

        // 对调用方法里的每一个参数，处理绑定
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = methodParameter.getType().newInstance();
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            wdb.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }

        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)) {
            this.messageConverter.write(returnObj, response);
        } else {
            // 返回前端页面
            if (returnObj instanceof ModelAndView) {
                mav = (ModelAndView) returnObj;
            } else if (returnObj instanceof String) {
                String sTarget = (String) returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }

        return mav;
    }

}
