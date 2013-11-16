package com.shopservice.domain;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Sets;
import com.shopservice.Services;
import org.codehaus.jackson.annotate.JsonIgnore;
import tyrex.services.UUID;

import javax.persistence.*;
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
    public double price;
    public String url;
    public Boolean published = false;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Group2Product> checks;

    @Transient
    public boolean checked;

    public ProductEntry(SqlRow row) {
        id = row.getString("id");
        productName = row.getString("product_name");
        categoryId = row.getString("category_id");
        productId = row.getString("product_id");
        price = row.getDouble("price");
        url = row.getString("url");
        published = row.getBoolean("published");

        if (row.getString("checked") != null)
            checked = row.getString("checked").equals("1") ;
    }

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
        price = product.price;
        url = product.url;
        published = product.published;
    }

    public static List<ProductEntry> findSelected(String clientSettingsId, int groupId)
    {
        List<SqlRow> rows = Ebean.createSqlQuery("SELECT product_entry.* FROM product_entry " +
                "JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.group_id = ? " +
                "WHERE client_settings_id = ?")
                .setParameter(1, groupId)
                .setParameter(2, clientSettingsId)
                .findList();

        List<ProductEntry> entries = new ArrayList<ProductEntry>();
        for (SqlRow row : rows)
            entries.add( new ProductEntry(row) );

        return entries;
    }

    public static List<ProductEntry> findAndRefresh(String clientId, String categoryId, int settingsId) throws SQLException {
        List<Product> products = Services.getProductDAO(clientId).getProducts(categoryId);

        Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
        for (Product product : products){

            productEntriesFromClient.add(new ProductEntry(product));
        }

        Set<ProductEntry> productEntriesFromSettings = Ebean.find(ProductEntry.class)
                .where().eq("client_settings_id", clientId).eq("category_id",categoryId).findSet();

        delete(Sets.difference(productEntriesFromSettings,productEntriesFromClient));

        add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));

        return getWithChecked(clientId, categoryId, settingsId);
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

    private static List<ProductEntry> getWithChecked(String clientId, String categoryId, int settingsId)
    {
        List<SqlRow> rows = Ebean.createSqlQuery("SELECT product_entry.*, group2product.id IS NOT NULL as checked FROM product_entry " +
                "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.group_id = ? " +
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


