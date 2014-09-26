package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.LinkedProductEntry;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class HibernateLinkedProductEntryRepositoryTest {
    private HibernateLinkedProductEntryRepository repository = new HibernateLinkedProductEntryRepository();
    private ClientSettings clientSettings;
    private ProductProvider productProvider;
    private ProductEntry productEntry;

    @Before
    public void setUp() throws Exception {
        HibernateProductProviderRepository providerRepository = new HibernateProductProviderRepository();
        HibernateClientSettingsRepository clientSettingsRepository = new HibernateClientSettingsRepository();
        HibernateProductEntryRepository productEntryRepository = new HibernateProductEntryRepository();

        clientSettings = new ClientSettings();

        clientSettings.id = UUID.randomUUID().toString();
        clientSettings.siteName = "myCompany";
        clientSettings.databaseUrl = "mysql:...";
        clientSettings.encoding = "cp1251";
        clientSettings.encoding = "cp1251";
        clientSettings.password = "fuckingUp";
        clientSettings.pathToProductImage = "http://...";
        clientSettings.pathToProductPage = "http://...";

        clientSettingsRepository.save(clientSettings);

        productProvider = new ProductProvider();
        productProvider.clientSettings = clientSettings;
        productProvider.name = "TestProvider";
        productProvider.url = "test.com";
        productProvider.margin = 10.0;

        providerRepository.create(clientSettings.id, productProvider);

        productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();

        productEntryRepository.add(clientSettings.id, Arrays.asList(productEntry));
    }

    @Test
    public void testCreate() throws Exception {
        LinkedProductEntry linkedProductEntry = new LinkedProductEntry();

        linkedProductEntry.name = "fuckUp";
        linkedProductEntry.productEntry = productEntry;
        linkedProductEntry.productProvider = productProvider;

        repository.create(linkedProductEntry);
    }

    @Test
    public void testUpdate() throws Exception {
        LinkedProductEntry linkedProductEntry = new LinkedProductEntry();

        linkedProductEntry.name = "motherfucker";
        linkedProductEntry.productEntry = productEntry;
        linkedProductEntry.productProvider = productProvider;

        repository.create(linkedProductEntry);

        linkedProductEntry.name = "fucking test";

        repository.update(linkedProductEntry);
    }

    @Test
    public void testDelete() throws Exception {
        LinkedProductEntry linkedProductEntry = new LinkedProductEntry();

        linkedProductEntry.name = "motherfucker1";
        linkedProductEntry.productEntry = productEntry;
        linkedProductEntry.productProvider = productProvider;

        repository.create(linkedProductEntry);

        repository.remove(linkedProductEntry.id);
    }
}
