package com.ant.minis.batis;

import com.ant.minis.jdbc.core.JdbcTemplate;
import com.ant.minis.jdbc.core.PreparedStatementCallback;

/**
 * <p>
 * Through this interface you can execute commands, get mappers and manage transactions.
 * </p>
 *
 * @author Ant
 * @since 2023/12/19 10:17
 */
public interface SqlSession {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback);
}
