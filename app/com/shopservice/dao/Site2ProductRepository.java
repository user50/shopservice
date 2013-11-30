package com.shopservice.dao;

public interface Site2ProductRepository {
    void set(String productId, int siteId, Boolean checked);

    void set(String clientId, String categoryId, int siteId, Boolean checked);

    void merge(int basic, int source);

    void difference(int basic, int source);
}
