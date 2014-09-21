package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by user50 on 21.09.2014.
 */
public class HibernateProductGroupRepositoryTest {

    private HibernateProductGroupRepository repository = new HibernateProductGroupRepository();
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
    public void testSave() throws Exception {
        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        repository.save(group);
    }

    @Test
    public void testGetAll() throws Exception {

        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        repository.save(group);

        repository.get(settings.id);

    }

    @Test
    public void testGetName() throws Exception {
        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        repository.save(group);

        repository.getName(group.id);
    }

    @Test
    public void testExist() throws Exception {
        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        repository.save(group);

       repository.exist(settings.id, "sdfsdf");

    }

    @Test
    public void testRemove() throws Exception {
        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        repository.save(group);

        repository.remove(group.id);

    }
}
