package com.ant.minis.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/8 17:47
 */
public interface StatementCallback {
    Object doInStatement(Statement statement) throws SQLException;
}
