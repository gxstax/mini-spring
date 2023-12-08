package com.ant.minis.web.converter;

/**
 * <p>
 * 对象映射
 * </p>
 *
 * @author Ant
 * @since 2023/12/4 13:43
 */
public interface ObjectMapper {
    void setDateFormat(String dateFormat);

    void setDecimalFormat(String decimalFormat);

    String writeValuesAsString(Object obj);
}
