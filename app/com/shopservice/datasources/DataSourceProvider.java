package com.shopservice.datasources;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 12/13/14.
 */
public interface DataSourceProvider {
    DataSource provide();
}
