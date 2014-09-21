package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by user50 on 20.09.2014.
 */
public class HibernateClientSettingsRepositoryTest {

    HibernateClientSettingsRepository repository = new HibernateClientSettingsRepository();

    @Test
    public void testSave() throws Exception {
        ClientSettings settings = new ClientSettings();

        settings.id = "foobar";
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
    public void testGetById() throws Exception {
        repository.get("foobar");
    }

    @Test
    public void testGetAll() throws Exception {
        repository.get();
    }

    @Test
    public void testRemove() throws Exception {
        repository.remove("foobar");

    }

    @Test
    public void testGetBySiteName() throws Exception {
        ClientSettings settings = new ClientSettings();

        settings.id = UUID.randomUUID().toString();
        settings.siteName = UUID.randomUUID().toString();
        settings.databaseUrl = "mysql:...";
        settings.encoding = "cp1251";
        settings.encoding = "cp1251";
        settings.password = "fuckingUp";
        settings.pathToProductImage = "http://...";
        settings.pathToProductPage = "http://...";

        repository.save(settings);

        repository.getBySiteName(settings.siteName);
    }

    @Test
    public void testUpdate() throws Exception {
        ClientSettings settings = new ClientSettings();

        settings.id = UUID.randomUUID().toString();
        settings.siteName = "myCompany";
        settings.databaseUrl = "mysql:...";
        settings.encoding = "cp1251";
        settings.encoding = "cp1251";
        settings.password = "fuckingUp";
        settings.pathToProductImage = "http://...";
        settings.pathToProductPage = "http://...";

        repository.save(settings);

        ClientSettings settingsToUpdate = new ClientSettings();

        settingsToUpdate.id = settings.id;
        settingsToUpdate.siteName = "fuckUp";
        settingsToUpdate.databaseUrl = "mysql:...";
        settingsToUpdate.encoding = "cp1251";
        settingsToUpdate.encoding = "cp1251";
        settingsToUpdate.password = "fuckingUp";
        settingsToUpdate.pathToProductImage = "http://...";
        settingsToUpdate.pathToProductPage = "http://...";

        repository.update(settingsToUpdate);

    }
}
