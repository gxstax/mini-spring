package com.ant.minis.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/8 18:00
 */
public interface PreparedStatementCallback {
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
