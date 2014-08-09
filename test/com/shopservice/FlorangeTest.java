package com.shopservice;

import com.shopservice.dao.FlorangeCategoryRepository;
import com.shopservice.dao.FlorangeProductRepository;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;
import com.shopservice.productsources.Florange;
import com.shopservice.productsources.PersistenceByFile;
import com.shopservice.productsources.ProductSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import play.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        FlorangeProductRepository repository = new FlorangeProductRepository();
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
}
