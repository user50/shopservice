package com.shopservice.com.shopservice.dao;

import com.shopservice.ProductConditions;
import com.shopservice.dao.CachedProductRepository;
import com.shopservice.dao.JdbcProductRepository;
import com.shopservice.dao.ProductRepository;
import org.junit.Test;

public class CachedProductRepositoryTest {

    ProductRepository repository = new CachedProductRepository(new JdbcProductRepository("client1"));

    @Test
    public void testFind() throws Exception {
        repository.find(new ProductConditions());
        repository.size(new ProductConditions());
    }
}
