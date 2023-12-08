package com.ant.minis.beans;

import com.ant.minis.util.NumberUtils;
import com.ant.minis.util.StringUtils;

import java.text.NumberFormat;

/**
 * <p>
 * Number类型参数编辑器
 * </p>
 *
 * @author Ant
 * @since 2023/11/30 18:04
 */
public class CustomNumberEditor implements PropertyEditor {

    private Class<? extends Number> numberClass;
    //数据类型
    private NumberFormat numberFormat;
    //指定格式
    private boolean allowEmpty;

    private Object value;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass,
                              NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else if (this.numberFormat != null) {
            // 给定格式
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    /***
     * <p>
     * 接收 object 作为参数
     * </p>
     *
     * @param value
     * @return void
     */
    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            this.value = (NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass));
        } else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    /**
     * <p>
     * 将 number 表示为格式化串
     * </p>
     *
     * @return java.lang.Object
     */
    @Override
    public Object getAsText() {
        Object value = this.value;
        if (value == null) {
            return "";
        }
        if (this.numberFormat != null) {
            // 给定格式
            return this.numberFormat.format(value);
        } else {
            return value.toString();
        }
    }
}
