package com.shopservice.dao;

import com.shopservice.domain.Product;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CachedProductRepository extends ProductRepositoryWrapper {

    private ProductRepository productRepository;

    private Map<String, List<Product>> categoryToProducts = new Hashtable<String, List<Product>>();

    public CachedProductRepository(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public List<Product> getProducts(String categoryId) {
        if (!categoryToProducts.containsKey(categoryId))
            categoryToProducts.put(categoryId, productRepository.getProducts(categoryId));
        else
            refreshCache(categoryId);

        return categoryToProducts.get(categoryId);
    }

    private void refreshCache(final String categoryId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                categoryToProducts.put(categoryId, productRepository.getProducts(categoryId));
            }
        }).start();
    }
}
