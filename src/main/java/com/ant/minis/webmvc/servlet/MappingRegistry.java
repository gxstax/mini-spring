package com.ant.minis.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/29 14:49
 */
public class MappingRegistry {
    /** 保存自定义的 @RequestMapping 名称 （URL 的名称）的列表 */
    private List<String> urlMappingNames = new ArrayList<>();

    /** 保存 URL 名称与对象的映射关系 */
    private Map<String, Object> mappingObjs = new HashMap<>();

    /** 保存 URL 名称与方法的映射关系 */
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjs() {
        return mappingObjs;
    }

    public void setMappingObjs(Map<String, Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }
}
