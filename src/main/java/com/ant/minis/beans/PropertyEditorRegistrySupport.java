package com.ant.minis.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 属性值编辑注册承接
 * </p>
 *
 * @author Ant
 * @since 2023/12/1 9:21
 */
public class PropertyEditorRegistrySupport {
    private Map<Class<?>, PropertyEditor> defaultEditors;
    private Map<Class<?>, PropertyEditor> customEditors;

    /**
     * <p>
     * 注册默认的转换器editor
     * </p>
     *
     * @return void
     */
    protected void registerDefaultEditors() {
        createDefaultEditors();
    }

    /**
     * <p>
     * 获取默认的转换器editor
     * </p>
     *
     * @param requiredType
     * @return com.ant.minis.beans.PropertyEditor
     */
    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        return this.defaultEditors.get(requiredType);
    }

    /**
     * <p>
     * 创建默认的转换器editor，对每一种数据类型规定一个默认的转换器
     * </p>
     *
     * @return void
     */
    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);
        // Default instances of collection editors.
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
        this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
        this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
        this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        this.defaultEditors.put(String.class, new StringEditor(String.class, true));
    }

    /**
     * <p>
     * 注册客户化转换器
     * </p>
     *
     * @param requiredType
     * @param propertyEditor
     * @return void
     */
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        if (this.customEditors == null) {
            this.customEditors = new LinkedHashMap<>(16);
        }
        this.customEditors.put(requiredType, propertyEditor);
    }

    /**
     * <p>
     * 查找客户化转换器
     * </p>
     *
     * @param requiredType
     * @return com.ant.minis.beans.PropertyEditor
     */
    public PropertyEditor findCustomEditor(Class<?> requiredType) {
        Class<?> requiredTypeToUse = requiredType;
        return getCustomEditor(requiredTypeToUse);
    }

    public boolean hasCustomEditorForElement(Class<?> elementType) {
        return (elementType != null && this.customEditors != null && this.customEditors.containsKey(elementType));
    }

    /**
     * <p>
     * 获取客户化转换器
     * </p>
     *
     * @param requiredType
     * @return com.ant.minis.beans.PropertyEditor
     */
    private PropertyEditor getCustomEditor(Class<?> requiredType) {
        if (requiredType == null || this.customEditors == null) {
            return null;
        }
        PropertyEditor editor = this.customEditors.get(requiredType);
        return editor;
    }

}
