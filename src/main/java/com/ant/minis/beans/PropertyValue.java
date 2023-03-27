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
    private final String type;
    private final String name;
    private final Object value;

    /**
     * 判断是否是引用类型
     */
    private final boolean isRef;


    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public boolean isRef() {
        return isRef;
    }
}

