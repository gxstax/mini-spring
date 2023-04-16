package com.ant.minis.beans.factory;


import com.ant.minis.beans.*;
import com.ant.minis.beans.factory.config.ConstructorArgumentValue;
import com.ant.minis.beans.factory.config.ConstructorArgumentValues;
import com.ant.minis.beans.factory.config.BeanDefinition;
import com.ant.minis.beans.factory.support.BeanDefinitionRegistry;
import com.ant.minis.beans.factory.support.DefaultSingletonBeanRegistry;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 简单 Bean 工厂
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 01:13
 **/
@Slf4j
public class SimpleBeanFactory {

//   private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

   private List<String> beanDefinitionNames = new ArrayList<>();

    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

   public SimpleBeanFactory() {

   }

}

