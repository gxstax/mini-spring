package com.ant.minis.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;

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

    public Object query(StatementCallback statementCallback) {
        Object returnObj = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://10.10.44.37:8836;databasename=test;user=root&password=mixc@mixc");

            con = dataSource.getConnection();

            stmt = con.createStatement();

            statementCallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnObj;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback callback) {
        Object returnObj = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://10.10.44.37:8836;databasename=test;user=root&password=mixc@mixc");

            pstmt = con.prepareStatement(sql);

            // 设置参数
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof String) {
                    pstmt.setString( i + 1, (String) arg);
                } else if (arg instanceof Integer) {
                    pstmt.setInt( i + 1, (Integer) arg);
                }
            }

            returnObj = callback.doInPreparedStatement(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnObj;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

}
