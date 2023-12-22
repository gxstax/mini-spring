package com.ant.minis.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * <p>
 * JDBC 执行流程模版
 * </p>
 *
 * @author Ant
 * @since 2023/12/8 17:04
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate() {}

    /**
     * <p>
     * 原始的JDBC查询
     * </p>
     *
     * @return java.lang.Object
     */
    public Object query() {
        Object returnObj = null;

        Connection con;
        Statement stmt;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://10.10.44.37:8836/test?useUnicode=true&characterEncoding=utf8&useSSL=true", "root", "mixc@mixc");

            stmt = con.createStatement();

            returnObj = stmt.executeQuery("select * from user where id = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnObj;
    }

    /**
     * <p>
     * 封装数据源dataSource + 自定义执行逻辑
     * </p>
     *
     * @param statementCallback
     * @return java.lang.Object
     */
    public Object query(StatementCallback statementCallback) {
        Connection con;
        Statement stmt;

        try {
            con = dataSource.getConnection();

            stmt = con.createStatement();

            return statementCallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * <p>
     * 统一处理参数值
     * </p>
     *
     * @param sql
     * @param args
     * @param callback
     * @return java.lang.Object
     */
    public Object query(String sql, Object[] args, PreparedStatementCallback callback) {
        Object returnObj = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 通过dataSource拿到数据库连接
            con = dataSource.getConnection();

            pstmt = con.prepareStatement(sql);

            // 通过 argumentSetter 统一设置参数值
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);

            returnObj = callback.doInPreparedStatement(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {}
        }

        return returnObj;
    }

    /**
     * <p>
     * 统一处理返回结果
     * </p>
     *
     * @param sql
     * @param args
     * @param rowMapper
     * @return java.util.List<T>
     */
    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> resultSetExtractor = new RowMapperResultSetExtractor<>(rowMapper);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 建立数据库连接
            con = dataSource.getConnection();
            // 准备 SQL 命令语句
            pstmt = con.prepareStatement(sql);

            // 通过 argumentSetter 统一设置参数值
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);

            rs = pstmt.executeQuery();

            return resultSetExtractor.extractData(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {}
        }
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

}
