package com.shopservice.domain;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import org.junit.Before;

public class ClientSettingsTest {

    EbeanServer server;

    @Before
    public void setUp() throws Exception {

        ServerConfig config = new ServerConfig();
        config.setName("h2");

        DataSourceConfig postgresDb = new DataSourceConfig();
        postgresDb.setDriver("org.h2.Driver");
        postgresDb.setUsername( "sa" );
        postgresDb.setPassword( "" );
        postgresDb.setUrl("jdbc:h2:~/test");
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
