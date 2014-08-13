package com.shopservice.dao;

import com.shopservice.domain.ProductProvider;

import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public interface ProductProviderRepository {

    public List<ProductProvider> find(String clientId);

    public ProductProvider create(String clientId, ProductProvider productProvider);

    public ProductProvider update(ProductProvider productProvider);

    public void remove(Integer providerId);

    public ProductProvider find(Integer providerId);
}
