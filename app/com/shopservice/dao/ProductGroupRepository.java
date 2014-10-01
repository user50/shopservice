package com.shopservice.dao;

import com.shopservice.domain.ProductGroup;

import java.util.List;

public interface ProductGroupRepository {
    List<ProductGroup> get(String clientId);

    void save(ProductGroup productGroup);

    String getName(int id);

    boolean exist(String clientId, String name);

    void remove(int id);

    ProductGroup get(Long groupId);

    void update(ProductGroup group);
}
