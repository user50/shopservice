package com.shopservice.domain;

import com.avaje.ebean.SqlRow;
import org.codehaus.jackson.annotate.JsonIgnore;
import tyrex.services.UUID;

import javax.persistence.*;
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
        categoryId = product.category.id;
        price = product.price;
        url = product.url;
        published = product.published;
    }
}


