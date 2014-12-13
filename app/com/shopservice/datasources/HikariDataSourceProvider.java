package com.shopservice.datasources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 12/13/14.
 */
public class HikariDataSourceProvider implements DataSourceProvider {

    private DataSource dataSource;

    public HikariDataSourceProvider(String url, int maxPoolSize) {
        HikariConfig config = new HikariConfig();
        config.setConnectionTestQuery("SELECT version()");
        config.setConnectionTimeout(10000L);
        config.setMaximumPoolSize(maxPoolSize);
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("url", url);
        config.setIdleTimeout(30000);
        dataSource = new HikariDataSource(config);

    }

    @Override
    public DataSource provide() {
        return dataSource;
    }
}
