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

import static com.shopservice.Services.productEntryService;

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

    public static Collection<ProductEntry> find(String clientId, String categoryId) throws SQLException {
        List<Product> products = Services.getDataBaseManager(clientId)
                        .executeQueryForList(new ProductQueryByCategory(clientId, categoryId));

        Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
        for (Product product : products)
            productEntriesFromClient.add(new ProductEntry(product));

        Set<ProductEntry> productEntriesFromSettings = new HashSet<ProductEntry>( productEntryService.get(clientId, categoryId) );

        productEntryService.delete(Sets.difference(productEntriesFromSettings,productEntriesFromClient));

        productEntryService.add( Sets.difference(productEntriesFromClient, productEntriesFromSettings), clientId);

        return productEntryService.get(clientId, categoryId);
    }
}
