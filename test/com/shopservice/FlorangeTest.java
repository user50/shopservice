package com.shopservice;

import com.shopservice.dao.FlorangeCategoryRepository;
import com.shopservice.dao.ProductRepositoryWithFilterInMemory;
import com.shopservice.productsources.Florange;
import com.shopservice.productsources.PersistByMongo;
import com.shopservice.transfer.Product;
import org.junit.Test;

import java.util.List;

/**
 * Created by user50 on 05.08.2014.
 */
public class FlorangeTest {
    public static final String SITE_URL = "http://florange.ua";
    @Test
    public void testName() throws Exception {
        ProductConditions conditions = new ProductConditions();
        conditions.categoryId = "994546208";

        conditions.offset = 123;
        conditions.limit = 3333;

        ProductRepositoryWithFilterInMemory repository = new ProductRepositoryWithFilterInMemory(new PersistByMongo( new Florange()));
        repository.find(conditions);
    }

    @Test
    public void testGetCategories() throws Exception {
        FlorangeCategoryRepository categoryRepository = new FlorangeCategoryRepository();
        System.out.println(categoryRepository.getCategories());
    }

    @Test
    public void testGetProducts() throws Exception {
        String url = "http://florange.ua/ru/production/business/learn/uspeh/";
        Florange florange = new Florange();
        List<Product> products = florange.getProducts(url);
    }

    @Test
    public void testProducts() throws Exception {
        new Florange().get(null);
    }
}
