package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import com.shopservice.domain.ProductProvider;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class HibernateProductProviderRepositoryTest {

    private HibernateProductProviderRepository repository = new HibernateProductProviderRepository();
    private ClientSettings clientSettings;

    @Before
    public void setUp() throws Exception {
        HibernateClientSettingsRepository repository = new HibernateClientSettingsRepository();

        clientSettings = new ClientSettings();

        clientSettings.id = UUID.randomUUID().toString();
        clientSettings.siteName = "myCompany";
        clientSettings.databaseUrl = "mysql:...";
        clientSettings.encoding = "cp1251";
        clientSettings.encoding = "cp1251";
        clientSettings.password = "fuckingUp";
        clientSettings.pathToProductImage = "http://...";
        clientSettings.pathToProductPage = "http://...";

        repository.save(clientSettings);
    }

    @Test
    public void testSave() throws Exception {
        ProductProvider productProvider = new ProductProvider();
        productProvider.clientSettings = clientSettings;
        productProvider.name = "TestProvider";
        productProvider.url = "test.com";
        productProvider.margin = 10.0;

        repository.create(clientSettings.id, productProvider);

    }

    @Test
    public void testUpdate() throws Exception {
        ProductProvider productProvider = new ProductProvider();
        productProvider.clientSettings = clientSettings;
        productProvider.name = "TestProvider1";
        productProvider.url = "test1.com";
        productProvider.margin = 10.0;

        repository.create(clientSettings.id, productProvider);

        productProvider.name = "TestProvider1Changed";
        productProvider.margin = 15.0;

        repository.update(productProvider);
    }

    @Test
    public void testRemove() throws Exception {
        ProductProvider productProvider = new ProductProvider();
        productProvider.clientSettings = clientSettings;
        productProvider.name = "TestProvider2";
        productProvider.url = "test2.com";
        productProvider.margin = 10.0;

        repository.create(clientSettings.id, productProvider);

        repository.remove(productProvider.id);
    }

    @Test
    public void testGet() throws Exception {
        ProductProvider productProvider = new ProductProvider();
        productProvider.clientSettings = clientSettings;
        productProvider.name = "TestProvider3";
        productProvider.url = "test3.com";
        productProvider.margin = 10.0;

        repository.create(clientSettings.id, productProvider);

        ProductProvider productProvider1 = repository.find(productProvider.id);

        Assert.assertEquals(productProvider.id, productProvider1.id);
        Assert.assertEquals(productProvider.name, productProvider1.name);
        Assert.assertEquals(productProvider.margin, productProvider1.margin);
    }

    @Test
    public void testFindList() throws Exception {
        ProductProvider productProvider = new ProductProvider();
        productProvider.clientSettings = clientSettings;
        productProvider.name = "TestProvider4";
        productProvider.url = "test4.com";
        productProvider.margin = 10.0;

        repository.create(clientSettings.id, productProvider);

        List<ProductProvider> providers = repository.find(clientSettings.id);

        Assert.assertEquals(providers.size(), 1);
        Assert.assertEquals(providers.get(0).name, "TestProvider4");
    }
}
