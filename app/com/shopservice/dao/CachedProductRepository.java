package com.shopservice.dao;

import com.shopservice.ProductConditions;
import com.shopservice.domain.Product;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CachedProductRepository extends ProductRepositoryWrapper {

    private Map<String, List<Product>> categoryToProducts = new Hashtable<String, List<Product>>();

    public CachedProductRepository(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public List<Product> find(ProductConditions query) {
        if (!query.productIds.isEmpty() || !query.words.isEmpty() || query.categoryId == null)
            return super.find(query);

        if (!categoryToProducts.containsKey(query.categoryId))
            categoryToProducts.put(query.categoryId, super.find(query));
        else
            refreshCache(query);

        return categoryToProducts.get(query.categoryId);
    }

    private void refreshCache(final ProductConditions query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                categoryToProducts.put(query.categoryId, productRepository.find(query));
            }
        }).start();
    }
}
