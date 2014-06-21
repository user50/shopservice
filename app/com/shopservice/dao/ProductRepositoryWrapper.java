package com.shopservice.dao;

import com.shopservice.domain.Product;

import java.util.Collection;
import java.util.List;

/**
 * Created by user50 on 21.06.2014.
 */
public class ProductRepositoryWrapper implements ProductRepository  {

    private ProductRepository productRepository;

    public ProductRepositoryWrapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts(String categoryId) {
        return productRepository.getProducts(categoryId);
    }

    @Override
    public List<Product> getProducts(Collection<String> productIds) {
        return productRepository.getProducts(productIds);
    }

    @Override
    public List<Product> findProductsByWords(List<String> words) {
        return productRepository.findProductsByWords(words);
    }
}
