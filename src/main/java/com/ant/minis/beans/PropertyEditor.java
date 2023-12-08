package com.ant.minis.beans;

/**
 * <p>
 * 属性编辑器
 * </p>
 *
 * @author Ant
 * @since 2023/11/30 18:02
 */
public interface PropertyEditor {
    void setAsText(String text);

    void setValue(Object value);

    Object getValue();

    Object getAsText();
}
