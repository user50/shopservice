package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Sets;
import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;

import java.util.*;

public class EbeanProductEntryRepository implements ProductEntryRepository {
    @Override
    public List<ProductEntry> findSelected(String clientSettingsId, int siteId) throws Exception {
        List<SqlRow> rows = Ebean.createSqlQuery("SELECT product_entry.* FROM product_entry " +
                "JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                "WHERE client_settings_id = ?")
                .setParameter(1, siteId)
                .setParameter(2, clientSettingsId)
                .findList();

        List<ProductEntry> entries = new ArrayList<ProductEntry>();
        for (SqlRow row : rows)
            entries.add( new ProductEntry(row) );

        return entries;
    }

    @Override
    public List<ProductEntry> findAndRefresh(String clientId, String categoryId, int settingsId) throws Exception {
        List<Product> products = Services.getProductDAO(clientId).getProducts(categoryId);

        Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
        for (Product product : products){

            productEntriesFromClient.add(new ProductEntry(product));
        }

        Set<ProductEntry> productEntriesFromSettings = Ebean.find(ProductEntry.class)
                .where().eq("client_settings_id", clientId).eq("category_id",categoryId).findSet();

        delete(Sets.difference(productEntriesFromSettings, productEntriesFromClient));

        add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));

        return getWithChecked(clientId, categoryId, settingsId);    }

    @Override
    public void add(String clientsId, Collection<ProductEntry> productsToAdd) throws Exception {
        ClientSettings clientSettings = Services.getClientSettingsDAO().findById( clientsId);
        clientSettings.productEntries.addAll(productsToAdd);
        Ebean.save(clientSettings);    }

    @Override
    public void delete(Collection<ProductEntry> productsToDelete) throws Exception {
        for (ProductEntry productEntry: productsToDelete){
            Ebean.delete(ProductEntry.class, productEntry);
        }
    }

    @Override
    public List<ProductEntry> getWithChecked(String clientId, String categoryId, int settingsId) throws Exception {
        List<SqlRow> rows = Ebean.createSqlQuery("SELECT product_entry.*, group2product.id IS NOT NULL as checked FROM product_entry " +
                "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                "WHERE client_settings_id = ? AND category_id = ? ")
                .setParameter(1, settingsId)
                .setParameter(2, clientId)
                .setParameter(3, categoryId)
                .findList();

        List<ProductEntry> entries = new ArrayList<ProductEntry>();
        for (SqlRow row : rows)
            entries.add(new ProductEntry(row));

        return entries;
    }
}
