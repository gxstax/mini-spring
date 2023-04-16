package com.ant.minis.beans.factory.support;


import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.PropertyValue;
import com.ant.minis.beans.PropertyValues;
import com.ant.minis.beans.factory.BeanFactory;
import com.ant.minis.beans.factory.config.BeanDefinition;
import com.ant.minis.beans.factory.config.ConstructorArgumentValue;
import com.ant.minis.beans.factory.config.ConstructorArgumentValues;
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
 * spring 抽象工厂基类
 * </p>
 *
 * @author Ant
 * @since 2023/4/5 23:46
 **/
@Slf4j
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>();

    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    public AbstractBeanFactory() {}

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * 获取 bean 实例
     * </p>
     *
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试直接拿Bean
        Object singleton = this.getSingleton(beanName);
        // 如果此时还没有这个 Bean 的实例， 则获取它的定义来创建实例
        if (null == singleton) {
            // 如果没有实例，则尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (null == singleton) {
                // 如果连毛胚都没有，则创建bean实例并注册
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (null == beanDefinition) {
                    throw new BeansException("Bean is not found");
                }
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName, singleton);
                // 进行 beanPostProcessor 位置
                // step 1: postProcessBeforeInitialization
                applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                // step 2: afterPropertiesSet
                // step 3: init-method
                if (null != beanDefinition.getInitMethodName() && !beanDefinition.equals("")) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // step 4: postProcessAfterInitialization
                applyBeanPostProcessorAfterInitialization(singleton, beanName);
            }

        }
        return singleton;
    }

    /** 
     * <p>
     * 调用bean定义的初始化方法
     * </p>
     * 
     * @param beanDefinition
     * @param obj
     */ 
    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class<?> clz = beanDefinition.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
        } catch (Exception e) {}
        try {
            method.invoke(obj);
        } catch (Exception e) {}
    }

    /**
     * <p>
     * 核心方法，创建bean实例
     * populates the bean instance, applies post-processors, etc.
     * </p>
     *
     * @param beanDefinition    bean描述对象
     * @return bean实例
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        // 创建毛胚bean实例
        Object obj = doCreateBean(beanDefinition);
        // 存放毛胚实例到缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);

        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (Exception e) {}

        // 处理属性
        handleProperties(beanDefinition, clz, obj);
        return obj;
    }

    /**
     * <p>
     * 创建毛胚bean实例
     * </p>
     *
     * @param beanDefinition
     * @return java.lang.Object
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues =
                    beanDefinition.getConstructorArgumentValues();
            // 如果有参数
            if (!constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>
                        [constructorArgumentValues.getArgumentCount()];
                Object[] paramValues = new
                        Object[constructorArgumentValues.getArgumentCount()];
                // 对每一个参数，分数据类型分别处理
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue =
                            constructorArgumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(constructorArgumentValue.getType()) ||
                            "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) ||
                            "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] =
                                Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String)
                                constructorArgumentValue.getValue());
                    } else { // 默认为string
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                try {
                    // 按照特定构造器创建实例
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (Exception e) {}
            } else { // 如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
        } catch (Exception e) {
        }
        return obj;
    }

    /**
     * <p>
     * 处理属性值
     * </p>
     *
     * @param bd
     * @param clz
     * @param obj
     */
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        log.info("handle properties for bean :" + bd.getId());
        // 处理属性
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                // 对每一个属性，分数据类型分别处理
                PropertyValue propertyValue =
                        propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                // 如果不是ref，只是普通属性
                if (!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType))
                    {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pType) ||
                            "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else { // 默认为string
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                } else {
                    // 如果是ref，则创建依赖的ref的bean
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    // 再次调用getBean创建ref的bean实例
                    try {
                        paramValues[0] = getBean((String)pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase()
                        + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (Exception e) {}
            }
        }
    }

    /**
     * <p>
     * 容器中是否包含 Bean
     * </p>
     *
     * @param beanName  指定的bean名称
     * @return 是否包含指定名称的bean
     */
    @Override
    public Boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    /**
     * <p>
     * 根据 BeanDefinition 注册实例到 Bean 工厂
     * </p>
     *
     * @param beanName 注册beanName
     * @param obj      注册类对象
     */
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    /**
     * <p>
     * 注册BeanDefinition
     * </p>
     *
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * <p>
     * 返回一个指定 BeanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     * @return com.ant.minis.beans.factory.config.BeanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    /**
     * <p>
     * 移除指定 beanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     */
    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitionMap.remove(beanName);
        this.beanDefinitionNames.remove(beanName);
        this.removeSingleton(beanName);
    }

    /**
     * <p>
     * 查找是否有指定 beanName 的 BeanDefinition
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    /**
     * <p>
     * 判断指定的 beanName 的 Bean 是否是单例模式
     * </p>
     *s
     * @param beanName
     * @return boolean
     */
    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    /**
     * <p>
     * 判断指定 beanName 的 Bean 是否是原型模式
     * </p>
     *
     * @param beanName
     * @return boolean
     */
    @Override
    public boolean isPrototype(String beanName) {
        return this.beanDefinitionMap.get(beanName).isPrototype();
    }

    /**
     * <p>
     * 获取bean的类型
     * </p>
     *
     * @param name
     * @return java.lang.Class<?>
     */
    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    /**
     * <p>
     * bean 初始化前的一些处理回调
     * </p>
     *
     * @param existingBean
     * @param beanName
     * @return java.lang.Object
     */
    abstract public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * <p>
     * bean 初始化后的一些处理回调
     * </p>
     *
     * @param existingBean
     * @param beanName
     * @return java.lang.Object
     */
    abstract public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;

}

