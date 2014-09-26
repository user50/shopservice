package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class HibernateProductEntryRepositoryTest {

    private HibernateProductEntryRepository repository = new HibernateProductEntryRepository();
    private ClientSettings settings;

    @Before
    public void setUp() throws Exception {
        HibernateClientSettingsRepository repository = new HibernateClientSettingsRepository();

        settings = new ClientSettings();

        settings.id = UUID.randomUUID().toString();
        settings.siteName = "myCompany";
        settings.databaseUrl = "mysql:...";
        settings.encoding = "cp1251";
        settings.encoding = "cp1251";
        settings.password = "fuckingUp";
        settings.pathToProductImage = "http://...";
        settings.pathToProductPage = "http://...";

        repository.save(settings);
    }

    @Test
    public void testAdd() throws Exception {
        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();

        repository.add(settings.id, Arrays.asList(productEntry));
    }

    @Test
    public void testFindByClientProductId() throws Exception {
        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();

        repository.add(settings.id, Arrays.asList(productEntry));

        repository.find(settings.id, productEntry.productId);

    }

    @Test
    public void testDelete() throws Exception {
        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();

        repository.add(settings.id, Arrays.asList(productEntry));

        repository.delete(Arrays.asList(productEntry));
    }
}
