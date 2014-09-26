package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ClientsCategory;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class HibernateClientsCategoryRepositoryTest {

    HibernateClientsCategoryRepository repository = new HibernateClientsCategoryRepository();
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
        ClientsCategory clientsCategory = new ClientsCategory();
        clientsCategory.name = "foo";

        repository.add(settings.id, clientsCategory);
    }

    @Test
    public void testGet() throws Exception {
        ClientsCategory clientsCategory = new ClientsCategory();
        clientsCategory.name = "foo";

        repository.add(settings.id, clientsCategory);

        repository.get(settings.id);
    }

    @Test
    public void testUpdate() throws Exception {
        ClientsCategory clientsCategory = new ClientsCategory();
        clientsCategory.name = "foo";

        repository.add(settings.id, clientsCategory);

        clientsCategory.name = "bar";
        repository.update(settings.id, clientsCategory);
    }

    @Test
    public void testDelete() throws Exception {
        ClientsCategory clientsCategory = new ClientsCategory();
        clientsCategory.name = "1234";

        repository.add(settings.id, clientsCategory);

        repository.delete(clientsCategory.id);
    }
}
