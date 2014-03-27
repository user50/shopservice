package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.shopservice.domain.Group2Product;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductGroup;

import java.util.Arrays;
import java.util.List;

public class EbeanGroup2ProductRepository implements Group2ProductRepository {
    @Override
    public void set(String productId, int groupId, Boolean checked) {
        if (checked)
            Ebean.createSqlUpdate("INSERT INTO group2product (`product_group_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                    "WHERE product_entry.id = ? AND group2product.id IS NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, groupId)
                    .setParameter(3, productId)
                    .execute();
        else
            Ebean.createSqlUpdate("DELETE group2product.* FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                    "WHERE product_entry.id = ? AND group2product.id IS NOT NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, productId)
                    .execute();
    }

    @Override
    public void set(String clientId, String categoryId, int groupId, Boolean checked) {
        if (checked)
            Ebean.createSqlUpdate("INSERT INTO group2product (`product_group_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                    "WHERE client_settings_id = ? AND category_id = ? AND group2product.id IS NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, groupId)
                    .setParameter(3, clientId)
                    .setParameter(4, categoryId)
                    .execute();
        else
            Ebean.createSqlUpdate("DELETE group2product.* FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                    "WHERE client_settings_id = ? AND category_id = ? AND group2product.id IS NOT NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, clientId)
                    .setParameter(3, categoryId)
                    .execute();
    }

    @Override
    public void merge(int basic, int source) {
        Ebean.createSqlUpdate( "INSERT INTO group2product ( `product_group_id`, `product_entry_id`) " +
                "SELECT ?, `source`.product_entry_id FROM ( " +
                " SELECT * FROM group2product " +
                " WHERE product_group_id = ? " +
                ") as `source` " +
                "LEFT JOIN group2product as basic ON `source`.product_entry_id = basic.product_entry_id " +
                "AND basic.product_group_id = ? " +
                "WHERE basic.id IS NULL" )
                .setParameter(1, basic).setParameter(2, source).setParameter(3, basic).execute();
    }

    @Override
    public void difference(int basic, int source) {
        Ebean.createSqlUpdate("DELETE basic.* FROM ( " +
                " SELECT * FROM group2product " +
                " WHERE product_group_id = ? " +
                ") AS `source` " +
                "JOIN group2product AS basic ON `source`.product_entry_id = basic.product_entry_id " +
                "AND basic.product_group_id = ?").setParameter(1, source).setParameter(2, basic).execute();
    }

    @Override
    public void set(String clientId, int groupId, List<String> productEntriesIds, boolean checked) {
        String[] abc = new String[productEntriesIds.size()];
        Arrays.fill(abc, "?");

        if (checked)
        {
            SqlUpdate sqlUpdate = Ebean.createSqlUpdate("INSERT INTO group2product (`product_group_id`, `product_entry_id`) " +
                    "SELECT ?, product_entry.id FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                    "WHERE client_settings_id = ? AND product_entry.id IN (" + Arrays.asList(abc).toString().replace("[", "").replace("]", "") + ") AND group2product.id IS NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, groupId)
                    .setParameter(3, clientId);

            int i = 4;
            for (String entryId : productEntriesIds)
                sqlUpdate.setParameter(i++, entryId);

            sqlUpdate.execute();
        }
        else
        {
            SqlUpdate sqlUpdate = Ebean.createSqlUpdate("DELETE group2product.* FROM product_entry " +
                    "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                    "WHERE client_settings_id = ? AND product_entry.id IN (" + Arrays.asList(abc).toString().replace("[", "").replace("]", "") + ") AND group2product.id IS NOT NULL")
                    .setParameter(1, groupId)
                    .setParameter(2, clientId);

            int i = 3;
            for (String entryId : productEntriesIds)
                sqlUpdate.setParameter(i++, entryId);

            sqlUpdate.execute();
        }
    }
}
