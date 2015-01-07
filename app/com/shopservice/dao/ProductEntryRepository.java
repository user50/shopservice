package com.shopservice.dao;

import com.shopservice.domain.ProductEntry;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductEntryRepository {
    List<ProductEntry> findSelected(String clientSettingsId, int groupId, boolean useCustomCategories) throws Exception;

    void add(String clientsId, Collection<ProductEntry> productsToAdd) throws Exception;

    void delete(Collection<ProductEntry> productsToDelete) throws Exception;

    Map<String,Integer> getCountPerCategory(String clientId, String groupId);

    Set<ProductEntry> get(String clientId, String categoryId);

    Set<ProductEntry> get(String clientId);

    List<ProductEntry> get(String clientId, int groupId, Collection<String> ids);

    List<ProductEntry> getWithCheckedPage(String clientId, String categoryId, int groupId, int offset, int limit);

    ProductEntry find(String productEntryId);

    ProductEntry find(String clientId, String clientsProductId);

    void update(ProductEntry entry);
}
