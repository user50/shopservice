package com.shopservice.domain;

import com.avaje.ebean.Ebean;

import javax.persistence.*;

@Entity
public class Group2Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public boolean checked;

    @ManyToOne
    public ProductGroup productGroup;

    @ManyToOne
    public ProductEntry productEntry;

    public Group2Product() {
    }

    public Group2Product(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public static void set(String productId, int groupId, Boolean checked) {

        if (checked)
            Ebean.createSqlUpdate("INSERT INTO group2product (`group_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.group_id = ? " +
                    "WHERE product_entry.id = ? AND group2product.id IS NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, groupId)
                    .setParameter(3, productId)
                    .execute();
        else
            Ebean.createSqlUpdate("DELETE group2product.* FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.group_id = ? " +
                    "WHERE product_entry.id = ? AND group2product.id IS NOT NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, productId)
                    .execute();
    }

    public static void set(String clientId, String categoryId, int groupId, Boolean checked) {

        if (checked)
            Ebean.createSqlUpdate("INSERT INTO group2product (`group_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.group_id = ? " +
                    "WHERE client_settings_id = ? AND category_id = ? AND group2product.id IS NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, groupId)
                    .setParameter(3, clientId)
                    .setParameter(4, categoryId)
                    .execute();
        else
            Ebean.createSqlUpdate("DELETE group2product.* FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.group_id = ? " +
                    "WHERE client_settings_id = ? AND category_id = ? AND group2product.id IS NOT NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, clientId)
                    .setParameter(3, categoryId)
                    .execute();


    }
}
