package com.ant.minis.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/18 16:19
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
