package com.shopservice;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

public class ClientSettingsTest {

    @Before
    public void setUp() throws Exception {
        Services.CLIENT_SETTINGS_SERVICE.getDatabaseManager().setConnectionPool(new ConnectionPool4Tests());

    }

    @Test
    public void testInServer() throws SQLException {
        String databaseUrl = Services.CLIENT_SETTINGS_SERVICE.getDatabaseUrl("shopopalo");
        assertThat(databaseUrl).isNull();
    }

    @Test
    public void testRefresh() throws JAXBException, FileNotFoundException, SQLException {
        PriceListService priceListService = new PriceListService();
        priceListService.refreshPriceList("client1",PriceListType.price);
    }
}
