package com.ant.minis.beans;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 多属性的的描述类
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 12:43
 **/
public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    /**
     * <p>
     * 属性值数量
     * </p>
     *
     * @return int
     */
    public int size() {
        return propertyValueList.size();
    }

    /**
     * <p>
     * 新增属性值
     * </p>
     *
     * @param value 属性
     */
    public void addPropertyValue(PropertyValue value) {
        this.propertyValueList.add(value);
    }

    /** 
     * <p>
     * 新增属性值
     * </p>
     * 
     * @param propertyName  属性名称
     * @param value         属性值
     */ 
    public void addPropertyValue(String propertyName, Object value) {
        this.propertyValueList.add(new PropertyValue(propertyName, value));
    }

    /**
     * <p>
     * 移除指定的属性值
     * </p>
     *
     * @param value
     */
    public void removePropertyValue(PropertyValue value) {
        this.propertyValueList.remove(value);
    }

    /**
     * <p>
     * 根据属性名称移除属性值
     * </p>
     *
     * @param propertyName 属性名称
     */
    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    private PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

    public Object get(String propertyName) {
        PropertyValue pv = getPropertyValue(propertyName);
        return null != pv ? pv.getValue() : null;
    }

    public boolean contains(String propertyName) {
        return null != getPropertyValue(propertyName);
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }


}

