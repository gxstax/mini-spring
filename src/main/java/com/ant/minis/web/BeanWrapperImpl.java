package com.ant.minis.web;

import com.ant.minis.beans.PropertyEditor;
import com.ant.minis.beans.PropertyEditorRegistrySupport;
import com.ant.minis.beans.PropertyValue;
import com.ant.minis.beans.PropertyValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/1 8:52
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    // 目标对象
    Object wrappedObject;

    Class<?> clz;

    // 参数值
    PropertyValues pvs;

    public BeanWrapperImpl(Object object) {
        registerDefaultEditors();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public void setBeanInstance(Object object) {
        this.wrappedObject = object;
    }

    /**
     * <p>
     * 绑定参数值
     * </p>
     *
     * @param pvs
     * @return void
     */
    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    /**
     * <p>
     * 绑定具体某个参数
     * </p>
     *
     * @param pv
     * @return void
     */
    public void setPropertyValue(PropertyValue pv) {
        // 拿到参数处理器
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        // 找到对应该参数类型的editor
        PropertyEditor pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        if (null == pe) {
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }

        // 设置参数值
        pe.setAsText((String) pv.getValue());
        propertyHandler.setValue(pe.getValue());
    }

    class BeanPropertyHandler {
        Method writeMethod = null;
        Method readMethod = null;

        Class<?> propertyClz = null;

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public BeanPropertyHandler(String propertyName) {
            try {
                // 获取对应的属性及类型
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();

                // 获取设置属性的方法，按照约定为 setXxxx()
                this.writeMethod = clz.getDeclaredMethod("set" +
                        propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                // 获取读属性的方法，按照约定为 getXxxx()
                this.readMethod = clz.getDeclaredMethod("get" +
                        propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * <p>
         * 调用 getter 读属性值
         * </p>
         *
         * @return java.lang.Object
         */
        public Object getValue() {
            Object result = null;
            writeMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * <p>
         * 调用setter设置属性值
         * </p>
         *
         * @param value
         * @return void
         */
        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
