package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductGroup;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class HibernateGroup2ProductRepositoryTest {
    private HibernateGroup2ProductRepository repository = new HibernateGroup2ProductRepository();
    private HibernateProductEntryRepository productEntryRepository = new HibernateProductEntryRepository();
    private HibernateProductGroupRepository productGroupRepository = new HibernateProductGroupRepository();

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
    public void testSetForListOfIds() throws Exception {
        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();
        productEntry.categoryId = UUID.randomUUID().toString();

        ProductEntry productEntry1 = new ProductEntry();
        productEntry1.id = UUID.randomUUID().toString();
        productEntry1.productId = UUID.randomUUID().toString();
        productEntry1.categoryId = productEntry.categoryId;

        productEntryRepository.add(settings.id, Arrays.asList(productEntry, productEntry1));

        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        productGroupRepository.save(group);

        repository.set(settings.id, group.id, Arrays.asList(productEntry.id, productEntry1.id), true);
        repository.set(settings.id, group.id, Arrays.asList(productEntry.id, productEntry1.id), false);
    }
}
