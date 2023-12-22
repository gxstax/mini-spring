package com.ant.minis.webmvc.mvc;

import com.ant.minis.beans.BeansException;
import com.ant.minis.context.ApplicationContext;
import com.ant.minis.context.ApplicationContextAware;
import com.ant.minis.web.bind.annotation.RequestMapping;
import com.ant.minis.web.method.HandlerMethod;
import com.ant.minis.webmvc.servlet.HandlerMapping;
import com.ant.minis.webmvc.servlet.MappingRegistry;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * <p>
 * 请求映射处理器
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 14:26
 */
public class RequestMappingHandlerMapping implements HandlerMapping, ApplicationContextAware {
    ApplicationContext applicationContext;
    private MappingRegistry mappingRegistry = null;

    public RequestMappingHandlerMapping() {
    }

    /**
     * <p>
     * 建立 URL 与调用方法和实例的映射关系，存储在 mappingRegistry 中
     * </p>
     *
     *
     * @return void
     */
    protected void initMappings() {
        Class<?> clz = null;
        Object obj = null;
        String[] controllerNames = this.applicationContext.getBeanDefinitionNames();
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                obj = this.applicationContext.getBean(controllerName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            Method[] methods = clz.getDeclaredMethods();
            if(methods!=null) {
                for(Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping) {
                        String methodName = method.getName();
                        String urlmapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlmapping);
                        this.mappingRegistry.getMappingObjs().put(urlmapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlmapping, method);
                        this.mappingRegistry.getMappingMethodNames().put(urlmapping, methodName);
                        this.mappingRegistry.getMappingClasses().put(urlmapping, clz);
                    }
                }
            }
        }

    }

    /***
     * <p>
     * 根据请求 URL 查找对应的调用方法
     * </p>
     *
     *
     * @param request
     * @return com.ant.minis.web.method.HandlerMethod
     */
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        if (this.mappingRegistry == null) { //to do initialization
            this.mappingRegistry = new MappingRegistry();
            initMappings();
        }

        String sPath = request.getServletPath();

        if (!this.mappingRegistry.getUrlMappingNames().contains(sPath)) {
            return null;
        }

        Method method = this.mappingRegistry.getMappingMethods().get(sPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);
        Class<?> clz = this.mappingRegistry.getMappingClasses().get(sPath);
        String methodName = this.mappingRegistry.getMappingMethodNames().get(sPath);

        HandlerMethod handlerMethod = new HandlerMethod(method, obj, clz, methodName);

        return handlerMethod;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
