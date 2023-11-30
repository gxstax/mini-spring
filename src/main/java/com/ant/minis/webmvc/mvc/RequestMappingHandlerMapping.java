package com.ant.minis.webmvc.mvc;

import com.ant.minis.web.RequestMapping;
import com.ant.minis.web.context.WebApplicationContext;
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
public class RequestMappingHandlerMapping implements HandlerMapping {
    WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    /**
     * <p>
     * 建立 URL 与调用方法和实例的映射关系，存储在 mappingRegistry 中
     * </p>
     *
     *
     * @return void
     */
    protected void initMapping() {
        Class<?> clz = null;
        Object obj = null;
        String[] controllerNames = this.wac.getBeanDefinitionNames();

        // 扫描wac中存放的所有bean
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
                obj = this.wac.getBean(controllerName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Method[] methods = clz.getDeclaredMethods();
            if (null != methods) {
                // 检查每一个方法声明
                for (Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping) {
                        String methodName = method.getName();
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();

                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlMapping, method);
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
        String servletPath = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(servletPath)) {
            return null;
        }

        Method method = this.mappingRegistry.getMappingMethods().get(servletPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(servletPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);
        return handlerMethod;
    }
}
