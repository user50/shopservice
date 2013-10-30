package com.shopservice.dao;

import com.shopservice.domain.Product;

import java.util.Collection;
import java.util.List;

public interface ProductDAO {
    List<Product> getProducts(String categoryId);

    List<Product> getProducts(Collection<String> productIds);
}
