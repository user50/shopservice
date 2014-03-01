package com.shopservice.domain;

import com.avaje.ebean.SqlRow;
import org.codehaus.jackson.annotate.JsonIgnore;
import tyrex.services.UUID;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductEntry {

    @Id
    public String id;

    @JsonIgnore
    public String productId;
    public String categoryId;

    @Transient
    public String productName;

    @Transient
    public double price;

    @Transient
    public String url;

    @Transient
    public Boolean published = false;

    @Transient
    public boolean checked;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Group2Product> checks;



    public ProductEntry(SqlRow row) {
        id = row.getString("id");
        categoryId = row.getString("category_id");
        productId = row.getString("product_id");

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


