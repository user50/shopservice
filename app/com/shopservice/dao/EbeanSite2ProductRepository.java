package com.shopservice.dao;

import com.avaje.ebean.Ebean;

public class EbeanSite2ProductRepository implements Site2ProductRepository{
    @Override
    public void set(String productId, int siteId, Boolean checked) {
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

    @Override
    public void set(String clientId, String categoryId, int siteId, Boolean checked) {
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

    @Override
    public void merge(int basic, int source) {
        Ebean.createSqlUpdate( "INSERT INTO site2product ( `site_id`, `product_entry_id`) " +
                "SELECT ?, `source`.product_entry_id FROM ( " +
                " SELECT * FROM site2product " +
                " WHERE site_id = ? " +
                ") as `source` " +
                "LEFT JOIN site2product as basic ON `source`.product_entry_id = basic.product_entry_id " +
                "AND basic.site_id = ? " +
                "WHERE basic.id IS NULL" )
                .setParameter(1, basic).setParameter(2, source).setParameter(1, basic).execute();
    }

    @Override
    public void difference(int basic, int source) {
        Ebean.createSqlUpdate("DELETE basic.* FROM ( " +
                " SELECT * FROM site2product " +
                " WHERE site_id = ? " +
                ") AS `source` " +
                "JOIN site2product AS basic ON `source`.product_entry_id = basic.product_entry_id " +
                "AND basic.site_id = ?").setParameter(1, source).setParameter(2, basic).execute();
    }
}
