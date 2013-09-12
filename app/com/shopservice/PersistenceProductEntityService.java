package com.shopservice;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;

import java.util.Collection;

public class PersistenceProductEntityService implements ProductEntryService {
    @Override
    public Collection<ProductEntry> get(String clientId, String categoryId) {
        return Ebean.find(ProductEntry.class)
                .where().eq("client_settings_id", clientId).eq("category_id",categoryId).findSet();
    }

    @Override
    public Collection<ProductEntry> getSelected(String clientId) {
        return Ebean.find(ProductEntry.class).where().eq("client_settings_id", clientId).eq("checked",true).findList();
    }

    @Override
    public Collection<ProductEntry> get(String clientId) {
        return Ebean.find(ProductEntry.class).where().eq("client_settings_id", clientId).findList();
    }

    @Override
    public void setChecked(boolean checked, String productEntryId) {
        Ebean.createSqlUpdate("UPDATE product_entry SET `checked`=:checked " +
                "WHERE id=:id")
                .setParameter("checked", checked)
                .setParameter("id", productEntryId).execute();
    }

    @Override
    public void setChecked(boolean checked, String clientId, String categoryId) {
        Ebean.createSqlUpdate("UPDATE product_entry SET `checked`=:checked " +
                "WHERE client_settings_id = :clientSettings AND category_id = :categoryId")
                .setParameter("checked", checked)
                .setParameter("clientSettings", clientId)
                .setParameter("categoryId", categoryId).execute();
    }

    @Override
    public void delete(Collection<ProductEntry> productsToDelete) {
        for (ProductEntry productEntry: productsToDelete){
            Ebean.delete(ProductEntry.class, productEntry);
        }
    }

    @Override
    public void add(Collection<ProductEntry> productsToAdd, String clientId) {
        ClientSettings clientSettings = ClientSettings.findById( clientId);
        clientSettings.productEntries.addAll(productsToAdd);
        Ebean.save(clientSettings);
    }
}
