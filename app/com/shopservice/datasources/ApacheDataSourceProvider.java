package com.shopservice.datasources;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 12/13/14.
 */
public class ApacheDataSourceProvider implements DataSourceProvider {

    private BasicDataSource dataSource;

    public ApacheDataSourceProvider(String url, int maxPoolSize) {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setMaxActive(maxPoolSize);
        dataSource.setMaxWait(10000);
        dataSource.setMaxIdle(10);
    }

    @Override
    public DataSource provide() {
        return dataSource;
    }
}
