package com.ant.minis.jdbc.datasource;

import com.ant.minis.jdbc.pool.PooledConnection;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/11 9:30
 */
public class PooledDataSource implements DataSource {
    private List<PooledConnection> connections = null;

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Properties connectionProperties;
    private Connection connection;

    private int initialSize = 2;

    /**
     * <p>
     * 默认构造函数
     * </p>
     *
     * @return
     */
    public PooledDataSource() {
    }

    private void initPool() {
        this.connections = new ArrayList<>(initialSize);
        try {
            for(int i = 0; i < initialSize; i++){
                Connection connect = DriverManager.getConnection(url, username, password);
                PooledConnection pooledConnection = new PooledConnection(connect, false);
                this.connections.add(pooledConnection);
                System.out.println("********add connection pool*********");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        try {
            Class.forName(this.driverClassName);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not load JDBC driver class [" + driverClassName + "]", ex);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private PooledConnection getAvailableConnection() throws SQLException{
        for(PooledConnection pooledConnection : this.connections){
            if (!pooledConnection.getActive()) {
                pooledConnection.setActive(true);
                return pooledConnection;
            }
        }

        return null;
    }

    /**
     * <p>
     * 将参数组织成 Properties 结构，然后拿到实际的数据库连接
     * </p>
     *
     * @param username
     * @param password
     * @return java.sql.Connection
     */
    protected Connection getConnectionFromDriver(String username, String password) throws SQLException {
        Properties mergedProps = new Properties();
        Properties connProps = getConnectionProperties();
        if (connProps != null) {
            mergedProps.putAll(connProps);
        }

        if (null != username) {
            mergedProps.setProperty("user", username);
        }

        if (null != password) {
            mergedProps.setProperty("password", password);
        }

        this.connection = getConnectionFromDriverManager(getUrl(), mergedProps);
        return this.connection;
    }

    /**
     * <p>
     * 通过 DriverManager.getConnection() 建立实际的连接
     * </p>
     *
     * @param url
     * @param props
     * @return java.sql.Connection
     */
    protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
        return DriverManager.getConnection(url, props);
    }
}
