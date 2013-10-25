package com.shopservice;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

public class ClientSettingsTest {

    @Test
    public void testRefresh() throws JAXBException, FileNotFoundException, SQLException {
        PriceListService priceListService = new PriceListService();
        priceListService.refreshPriceList("client1", PriceListType.price);
    }

}
