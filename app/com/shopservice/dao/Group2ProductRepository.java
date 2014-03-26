package com.shopservice.dao;

import com.shopservice.domain.ProductEntry;

import java.util.List;

public interface Group2ProductRepository {
    void set(String productId, int groupId, Boolean checked);

    void set(String clientId, String categoryId, int groupId, Boolean checked);

    void merge(int basic, int source);

    void difference(int basic, int source);

    void set(String clientId, int groupId, List<ProductEntry> productEntries);
}
