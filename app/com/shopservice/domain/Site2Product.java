package com.shopservice.domain;

import com.avaje.ebean.Ebean;

import javax.persistence.*;

@Entity
public class Site2Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public boolean checked;

    @ManyToOne
    public Site site;

    @ManyToOne
    public ProductEntry productEntry;

    public Site2Product() {
    }

    public Site2Product(Site site) {
        this.site = site;
    }

    public static void set(String productId, int siteId, Boolean checked) {

        if (checked)
            Ebean.createSqlUpdate("INSERT INTO site2product (`site_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN site2product ON site2product.product_entry_id = product_entry.id AND site2product.site_id = ? " +
                    "WHERE product_entry.id = ? AND site2product.id IS NULL")
                    .setParameter(1, siteId)
                    .setParameter(2, siteId)
                    .setParameter(3, productId)
                    .execute();
        else
            Ebean.createSqlUpdate("DELETE site2product.* FROM product_entry " +
                    "LEFT JOIN site2product ON site2product.product_entry_id = product_entry.id AND site2product.site_id = ? " +
                    "WHERE product_entry.id = ? AND site2product.id IS NOT NULL")
                    .setParameter(1, siteId)
                    .setParameter(2, productId)
                    .execute();
    }

    public static void set(String clientId, String categoryId, int siteId, Boolean checked) {

        if (checked)
            Ebean.createSqlUpdate("INSERT INTO site2product (`site_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN site2product ON site2product.product_entry_id = product_entry.id AND site2product.site_id = ? " +
                    "WHERE client_settings_id = ? AND category_id = ? AND site2product.id IS NULL")
                    .setParameter(1, siteId)
                    .setParameter(2, siteId)
                    .setParameter(3, clientId)
                    .setParameter(4, categoryId)
                    .execute();
        else
            Ebean.createSqlUpdate("DELETE site2product.* FROM product_entry " +
                    "LEFT JOIN site2product ON site2product.product_entry_id = product_entry.id AND site2product.site_id = ? " +
                    "WHERE client_settings_id = ? AND category_id = ? AND site2product.id IS NOT NULL")
                    .setParameter(1, siteId)
                    .setParameter(2, clientId)
                    .setParameter(3, categoryId)
                    .execute();


    }
}
