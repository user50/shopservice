package com.shopservice;

import com.shopservice.domain.ClientSettings;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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

        service.createClientSettings(settings);
    }

    @Test
    public void testCreateClientSettings() throws Exception {
        settings.id = null;
        service.createClientSettings(settings);

        assertThat(settings.id != null);
        assertThat(service.getClientSettings(settings.id)).isNotNull();
    }

    @Test
    public void testUpdateClientSettings() throws Exception {
        settings.siteName = "MyCompany";
        service.updateClientSettings(settings);

        ClientSettings updatedClientSettings = service.getClientSettings(settings.id);

        assertThat(updatedClientSettings).isNotNull();
        assertThat(updatedClientSettings.siteName).isEqualTo(settings.siteName);
    }

    @Test
    public void testRemoveClientSettings() throws Exception {
        service.removeClientSettings(settings.id);

        assertThat(service.getClientSettings(settings.id)).isNull();

    }

    @Test
    public void testSetProductIds() throws Exception {
        List<String> products = Arrays.asList("1","2","3");
        service.setProductIds(settings.id, products);

        List<String> savedProducts = service.getProductIds(settings.id);

        assertThat(savedProducts).isNotEmpty();
        assertThat(savedProducts).isEqualTo(products);


    }

    @Test
    public void testRefresh() throws JAXBException, FileNotFoundException, SQLException {
        PriceListService priceListService = new PriceListService();
        priceListService.refreshPriceList("client1", PriceListType.price);
    }
}
