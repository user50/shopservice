package com.shopservice.dao;

import com.shopservice.domain.ProductEntry;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductEntryRepository {
    List<ProductEntry> findSelected(String clientSettingsId, int siteId) throws Exception;

    List<ProductEntry> findAndRefresh(String clientId, String categoryId, int settingsId) throws Exception;

    void add(String clientsId, Collection<ProductEntry> productsToAdd) throws Exception;

    void delete(Collection<ProductEntry> productsToDelete) throws Exception;

    List<ProductEntry> getWithChecked(String clientId, String categoryId, int settingsId) throws Exception;

    Map<String,Integer> getCountPerCategory(String clientId, String groupId);

}
