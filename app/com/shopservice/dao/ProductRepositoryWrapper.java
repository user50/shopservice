package com.shopservice.dao;

import com.shopservice.ProductConditions;
import com.shopservice.domain.Product;

import java.util.List;

/**
 * Created by user50 on 21.06.2014.
 */
public class ProductRepositoryWrapper implements ProductRepository  {

    protected ProductRepository productRepository;

    public ProductRepositoryWrapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> find(ProductConditions query) {
        return productRepository.find(query);
    }

    @Override
    public List<Product> find() {
        return productRepository.find();
    }

    @Override
    public int size(ProductConditions conditions) {
        return productRepository.size(conditions);
    }
}
