package com.shopservice;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.shopservice.domain.*;
import org.junit.Before;
import org.junit.Test;

public class ClientSettingsTest {

    EbeanServer server;

    @Before
    public void setUp() throws Exception {

        ServerConfig config = new ServerConfig();
        config.setName("mysql");

        DataSourceConfig postgresDb = new DataSourceConfig();
        postgresDb.setDriver("com.mysql.jdbc.Driver");
        postgresDb.setUsername( "root" );
        postgresDb.setPassword( "neuser50" );
        postgresDb.setUrl("jdbc:mysql://localhost:3306/shopservice");
        postgresDb.setHeartbeatSql("select count(*) from t_one");

        config.setDataSourceConfig(postgresDb);

        config.setDdlGenerate(true);
        config.setDdlRun(true);

        config.setDefaultServer(false);
        config.setRegister(false);


        config.addClass(ClientSettings.class);
        config.addClass(ProductEntry.class);
        config.addClass(ProductGroup.class);
        config.addClass(Group2Product.class);

        server = EbeanServerFactory.create(config);
    }


}
