package com.ant.minis.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 * 参数预设置
 * </p>
 *
 * @author Ant
 * @since 2023/12/18 8:55
 */
public class ArgumentPreparedStatementSetter {
    // 参数数组
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    public void setValues(PreparedStatement pstmt) throws SQLException {
        if (null != this.args) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                doSetValue(pstmt, i + 1, arg);
            }
        }
    }

    /**
     * <p>
     * 设置参数值
     * </p>
     *
     * @param pstmt
     * @param parameterPosition
     * @param argValue
     * @return void
     */
    protected void doSetValue(PreparedStatement pstmt, int parameterPosition, Object argValue) throws SQLException {
        Object arg = argValue;
        // 判断参数类型，调用相应的JDBC set方法
        if (arg instanceof String) {
            pstmt.setString(parameterPosition, (String)arg);
        } else if (arg instanceof Integer) {
            pstmt.setInt(parameterPosition, (int)arg);
        } else if (arg instanceof java.util.Date) {
            pstmt.setDate(parameterPosition, new java.sql.Date(((java.util.Date)arg).getTime()));
        }
    }
}
