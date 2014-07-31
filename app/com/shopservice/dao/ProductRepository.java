package com.shopservice.dao;

import com.shopservice.ProductConditions;
import com.shopservice.transfer.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> find(ProductConditions conditions);

    List<Product> find();

    int size(ProductConditions conditions);
}
