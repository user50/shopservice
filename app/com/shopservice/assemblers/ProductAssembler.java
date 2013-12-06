package com.shopservice.assemblers;

import com.shopservice.Services;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.dao.ProductRepository;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAssembler {

    ProductEntryRepository productEntryRepository;
    ProductRepository productRepository;

    public ProductAssembler(ProductEntryRepository productEntryRepository, ProductRepository productRepository) {
        this.productEntryRepository = productEntryRepository;
        this.productRepository = productRepository;
    }

    public Collection<Product> getProducts(String clientId, String categoryId, int groupId) throws Exception {
        Map<String,Product> products = new HashMap<String, Product>();
        for (Product product : productRepository.getProducts(categoryId))
            products.put(product.id, product);

        for (ProductEntry o : productEntryRepository.getWithChecked(clientId, categoryId, groupId) ){

        }


       return null;
    }
}
