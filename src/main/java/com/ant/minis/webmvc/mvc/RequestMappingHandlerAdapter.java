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

    public RequestMappingHandlerAdapter() {}

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response,
                                        HandlerMethod handler) {
        ModelAndView mv = null;

        try {
            mv = invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;

    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request,
                                               HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {

        WebDataBinderFactory binderFactory = new WebDataBinderFactory();

        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            if (methodParameter.getType()!=HttpServletRequest.class && methodParameter.getType()!=HttpServletResponse.class) {
                Object methodParamObj = methodParameter.getType().newInstance();
                WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
                webBindingInitializer.initBinder(wdb);
                wdb.bind(request);
                methodParamObjs[i] = methodParamObj;
            } else if (methodParameter.getType()==HttpServletRequest.class) {
                methodParamObjs[i] = request;
            } else if (methodParameter.getType()==HttpServletResponse.class) {
                methodParamObjs[i] = response;
            }
            i++;
        }

        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        Class<?> returnType = invocableMethod.getReturnType();

        ModelAndView mav = null;
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)){ //ResponseBody
            this.messageConverter.write(returnObj, response);
        }
        else if (returnType == void.class) {

        }
        else {
            if (returnObj instanceof ModelAndView) {
                mav = (ModelAndView)returnObj;
            }
            else if(returnObj instanceof String) {
                String sTarget = (String)returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }

        return mav;
    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }
}
