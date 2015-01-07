package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductGroup;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class HibernateProductEntryRepositoryTest {

    private HibernateProductEntryRepository repository = new HibernateProductEntryRepository();
    private HibernateGroup2ProductRepository group2ProductRepository = new HibernateGroup2ProductRepository();
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

    @Test
    public void testGetCountPerCategory() throws Exception {
        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();
        productEntry.categoryId = UUID.randomUUID().toString();

        ProductEntry productEntry1 = new ProductEntry();
        productEntry1.id = UUID.randomUUID().toString();
        productEntry1.productId = UUID.randomUUID().toString();
        productEntry1.categoryId = productEntry.categoryId;

        repository.add(settings.id, Arrays.asList(productEntry, productEntry1));

        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        productGroupRepository.save(group);
        group2ProductRepository.set(productEntry.id, group.id, true);
        group2ProductRepository.set(productEntry1.id, group.id, true);

        repository.getCountPerCategory(settings.id, group.id.toString());

    }

    @Test
    public void testGetByCategoryId() throws Exception {
        String categoryId = UUID.randomUUID().toString();

        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();
        productEntry.categoryId = categoryId;

        ProductEntry productEntry1 = new ProductEntry();
        productEntry1.id = UUID.randomUUID().toString();
        productEntry1.productId = UUID.randomUUID().toString();
        productEntry1.categoryId = categoryId;

        repository.add(settings.id, Arrays.asList(productEntry, productEntry1));

        repository.get(settings.id, categoryId);
    }


    @Test
    public void testGetWithCheckedPage() throws Exception {
        ProductEntry productEntry = new ProductEntry();
        productEntry.id = UUID.randomUUID().toString();
        productEntry.productId = UUID.randomUUID().toString();
        productEntry.categoryId = UUID.randomUUID().toString();

        repository.add(settings.id, Arrays.asList(productEntry));

        ProductGroup group = new ProductGroup();
        group.name = "foo";
        group.clientSettings = settings;

        productGroupRepository.save(group);

        group2ProductRepository.set(productEntry.id, group.id, true);

        repository.getWithCheckedPage(settings.id, productEntry.categoryId,  group.id, 0, 10);

    }
}
