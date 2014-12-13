package com.shopservice.datasources;

import snaq.db.DBPoolDataSource;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 12/13/14.
 */
public class SnaqDataSourceProvider implements DataSourceProvider {

    private DBPoolDataSource dataSource;

    public SnaqDataSourceProvider(String url, int maxPoolSize) {
        dataSource = new DBPoolDataSource();
        dataSource.setName("pool-ds");
        dataSource.setDescription("Pooling DataSource");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setMinPool(1);
        dataSource.setMaxPool(maxPoolSize);
        dataSource.setIdleTimeout(3600);  // Specified in second.
        dataSource.setValidationQuery("SELECT 1 ");
    }

    @Override
    public DataSource provide() {
        return dataSource;
    }
}
