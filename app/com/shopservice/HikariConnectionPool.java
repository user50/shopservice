package com.shopservice;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 05.12.13
 * Time: 7:31
 * To change this template use File | Settings | File Templates.
 */
public class HikariConnectionPool implements ConnectionPool {

    private static final int CONNECTION_POOL_SIZE = 4;

    DataSource dataSource;

    public HikariConnectionPool(String url) {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(CONNECTION_POOL_SIZE);
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("url", url);
        config.setAcquireRetries(0);

        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
