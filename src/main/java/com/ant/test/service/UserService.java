package com.ant.test.service;

import com.ant.minis.batis.SqlSession;
import com.ant.minis.batis.SqlSessionFactory;
import com.ant.minis.beans.factory.annotation.Autowired;
import com.ant.minis.jdbc.core.JdbcTemplate;
import com.ant.minis.jdbc.core.RowMapper;
import com.ant.test.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/8 17:31
 */
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public User getUserInfo(int userid) {
        String sql = "select id, name,birthday from user where id="+userid;

        return (User) jdbcTemplate.query((stmt) -> {
            ResultSet rs = stmt.executeQuery(sql);
            User rtnUser = null;
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(userid);
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
            }
            return rtnUser;
        });
    }

    public User getUserInfoV2(int userid) {
        final String sql = "select id, name,birthday from users where id= ?";

        return (User) jdbcTemplate.query(sql, new Object[]{new Integer(userid)}, (pstmt) -> {
            ResultSet rs = pstmt.executeQuery(sql);
            User rtnUser = null;
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(userid);
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
            }
            return rtnUser;
        });
    }

    public List<User> getUserInfoV3(int userid) {
        final String sql = "select id, name,birthday from users where id= ?";

        return (List<User>) jdbcTemplate.query(sql, new Object[]{new Integer(userid)}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User rtnUser = new User();
                rtnUser.setId(rs.getInt("id"));
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new Date(rs.getDate("birthday").getTime()));
                return rtnUser;
            }
        });
    }

    public List<User> getUserInfoV4(int userid) {
        String sqlid = "com.ant.test.entity.User.getUserInfo";
        SqlSession sqlSession = sqlSessionFactory.openSession();

        return (List<User>) sqlSession.selectOne(sqlid, new Object[]{new Integer(userid)},
                (pstmt) -> {
                    ResultSet rs = pstmt.executeQuery();
                    User rtnUser = null;
                    if (rs.next()) {
                        rtnUser = new User();
                        rtnUser.setId(userid);
                        rtnUser.setName(rs.getString("name"));
                        rtnUser.setBirthday(new Date(rs.getDate("birthday").getTime()));
                    } else {

                    }
                    return rtnUser;
                }
        );
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
