package com.shopservice;

import com.shopservice.domain.ClientSettings;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

public class ClientSettingsTest {

    private ClientSettingsService service = Services.CLIENT_SETTINGS_SERVICE;
    private ClientSettings settings;


    @Before
    public void setUp() throws Exception {
        Services.CLIENT_SETTINGS_SERVICE.getDatabaseManager().setConnectionPool(new ConnectionPool4Tests());

        settings = new ClientSettings();
        settings.databaseUrl = "MyDB";
        settings.siteUrl = "http://example.com";
        settings.siteName = "example";


    }

    @Test
    public void testCreateClientSettings() throws Exception {
        service.createClientSettings(settings);

        assertThat(settings.id != null);
        assertThat(service.getClientSettings(settings.id)).isNotNull();
    }

    @Test
    public void testUpdateClientSettings() throws Exception {
        settings.

    }

    @Test
    public void testInServer() throws SQLException {
        String databaseUrl = Services.CLIENT_SETTINGS_SERVICE.getDatabaseUrl("shopopalo");
        assertThat(databaseUrl).isNull();
    }

    @Test
    public void testName() throws Exception {
        Services.CLIENT_SETTINGS_SERVICE.getClientSettings("1");

    }

    @Test
    public void testRefresh() throws JAXBException, FileNotFoundException, SQLException {
        PriceListService priceListService = new PriceListService();
        priceListService.refreshPriceList("client1", PriceListType.price);
    }
}
