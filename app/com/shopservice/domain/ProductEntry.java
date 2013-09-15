package com.shopservice.domain;

import com.avaje.ebean.Ebean;
import com.google.common.collect.Sets;
import com.shopservice.Services;
import com.shopservice.queries.ProductQueryByCategory;
import org.codehaus.jackson.annotate.JsonIgnore;
import tyrex.services.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.*;

@Entity
public class ProductEntry {

    @Id
    public String id;

    @JsonIgnore
    public String productId;
    public String productName;
    public String categoryId;
    public boolean checked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntry that = (ProductEntry) o;

        if (!categoryId.equals(that.categoryId)) return false;
        if (!productId.equals(that.productId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + categoryId.hashCode();
        return result;
    }

    public ProductEntry() {
    }

    public ProductEntry(Product product) {
        id = UUID.create();
        productId = product.id;
        productName = product.name;
        categoryId = product.categoryId;
    }

    public static List<ProductEntry> find(String clientSettingsId)
    {
        return Ebean.find(ProductEntry.class).where().eq("client_settings_id", clientSettingsId).findList();
    }

    public static List<ProductEntry> findSelected(String clientSettingsId)
    {
        return Ebean.find(ProductEntry.class).where().eq("client_settings_id", clientSettingsId).eq("checked",true).findList();
    }

    public static List<ProductEntry> find(String clientId, String categoryId) throws SQLException {
        List<Product> products = Services.getClientsInformationProvider(clientId).getProducts(categoryId);

        Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
        for (Product product : products)
            productEntriesFromClient.add(new ProductEntry(product));

        Set<ProductEntry> productEntriesFromSettings = Ebean.find(ProductEntry.class)
                                .where().eq("client_settings_id", clientId).eq("category_id",categoryId).findSet();

        delete(Sets.difference(productEntriesFromSettings,productEntriesFromClient));

        add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));

        return Ebean.find(ProductEntry.class).where().eq("client_settings_id", clientId).eq("category_id",categoryId).findList();
    }

    private static void add(String clientsId, Collection<ProductEntry> productsToAdd) {
        ClientSettings clientSettings = ClientSettings.findById( clientsId);
        clientSettings.productEntries.addAll(productsToAdd);
        Ebean.save(clientSettings);
    }

    public static void delete(Collection<ProductEntry> productsToDelete){
        for (ProductEntry productEntry: productsToDelete){
            Ebean.delete(ProductEntry.class, productEntry);
        }
    }
}
