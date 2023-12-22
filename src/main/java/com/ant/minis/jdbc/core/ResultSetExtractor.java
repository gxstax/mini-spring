package com.ant.minis.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/18 16:26
 */
public interface ResultSetExtractor<T> {
    T extractData(ResultSet rs) throws SQLException;
}
