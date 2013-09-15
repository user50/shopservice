package com.shopservice.clientsinform;

import com.shopservice.domain.Category;
import com.shopservice.domain.Product;

import java.util.Collection;
import java.util.List;

public interface ClientsInformationProvider {
    List<Product> getProducts(String categoryId);

    List<Product> getProducts(Collection<String> productId);

    List<Category> getCategories();
}
