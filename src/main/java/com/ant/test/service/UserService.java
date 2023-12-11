package com.ant.test.service;

import com.ant.minis.beans.factory.annotation.Autowired;
import com.ant.minis.jdbc.core.JdbcTemplate;
import com.ant.test.entity.User;

import java.sql.ResultSet;

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

    public User getUserInfo(int userid) {
        String sql = "select id, name,birthday from users where id="+userid;

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
}
