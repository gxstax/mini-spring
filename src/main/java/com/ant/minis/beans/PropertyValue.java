package com.ant.minis.beans;


/**
 * <p>
 * 描述类的属性字段
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 12:32
 **/
public class PropertyValue {
    private final String name;
    private final Object value;


    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}

