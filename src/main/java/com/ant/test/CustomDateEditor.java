package com.ant.test;

import com.ant.minis.beans.PropertyEditor;
import com.ant.minis.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 自定义属性编辑器
 * </p>
 *
 * @author Ant
 * @since 2023/12/1 15:34
 */
public class CustomDateEditor implements PropertyEditor {

    private Class<Date> dateClass;

    private DateTimeFormatter datetimeFormatter;

    private boolean allowEmpty;

    private Date value;

    public CustomDateEditor() {
        this(Date.class, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<Date> dateClass) {
        this(dateClass, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<Date> dateClass, boolean allowEmpty) {
        this(dateClass, "yyyy-MM-dd", allowEmpty);
    }

    public CustomDateEditor(Class<Date> dateClass, String pattern, boolean allowEmpty) {
        this.dateClass = dateClass;
        this.datetimeFormatter = DateTimeFormatter.ofPattern(pattern);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            LocalDate localDate = LocalDate.parse(text, datetimeFormatter);
            setValue(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    @Override
    public void setValue(Object value) {
        this.value = (Date) value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getAsText() {
        Date value = this.value;
        if (null == value) {
            return "";
        } else {
            LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(datetimeFormatter);
        }
    }
}
