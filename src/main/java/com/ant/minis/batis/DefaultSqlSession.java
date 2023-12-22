package com.ant.minis.batis;

import com.ant.minis.jdbc.core.JdbcTemplate;
import com.ant.minis.jdbc.core.PreparedStatementCallback;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/20 14:29
 */
public class DefaultSqlSession implements SqlSession {

    JdbcTemplate jdbcTemplate;

    SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback) {
        System.out.println("sqlid = " + sqlid);
        String sql = sqlSessionFactory.getMapperNode(sqlid).getSql();
        System.out.println("sql = " + sql);

        return jdbcTemplate.query(sql, args, pstmtcallback);
    }
}
