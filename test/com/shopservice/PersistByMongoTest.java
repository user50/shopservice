package com.shopservice;

import com.shopservice.productsources.PersistByMongo;
import com.shopservice.productsources.ProductSource;
import com.shopservice.transfer.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 09.08.2014.
 */
public class PersistByMongoTest {
    @Test
    public void testName() throws Exception {
        PersistByMongo source = new PersistByMongo(new TestSource());

        source.get(null);

    }

    private static class TestSource implements ProductSource
    {
        List<Product> products = new ArrayList<>();

        private TestSource() {
            products.add(new Product("1","fuck"));
            products.add(new Product("2","foo"));
            products.add(new Product("3","bar"));

        }

        @Override
        public List<Product> get(Integer providerId) {
            return products;
        }
    }
}
