package com.shopservice.dao;

import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

import static com.shopservice.HibernateUtil.*;

public class HibernateGroup2ProductRepository implements Group2ProductRepository{
    @Override
    public void set(final String productId, final int groupId, Boolean checked) {
        if (checked)
            execute(new Update() {
                @Override
                public void execute(Session session) {
                    session.createSQLQuery("INSERT INTO group2product (`product_group_id`, `product_entry_id`) " +
                            "SELECT ?, product_entry.id FROM product_entry " +
                            "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                            "WHERE product_entry.id = ? AND group2product.id IS NULL")
                            .setParameter(1, groupId)
                            .setParameter(2, groupId)
                            .setParameter(3, productId)
                            .executeUpdate();
                }
            });
        else
            execute(new Update() {
                @Override
                public void execute(Session session) {
                    session.createSQLQuery("DELETE group2product.* FROM product_entry " +
                            "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                            "WHERE product_entry.id = ? AND group2product.id IS NOT NULL")
                            .setParameter(1, groupId)
                            .setParameter(2, productId)
                            .executeUpdate();
                }
            });
    }

    @Override
    public void set(final String clientId, final String categoryId, final int groupId, Boolean checked) {
        if (checked)
            execute(new Update() {
                @Override
                public void execute(Session session) {
                    session.createSQLQuery("INSERT INTO group2product (`product_group_id`, `product_entry_id`) " +
                            "SELECT ?, product_entry.id FROM product_entry " +
                            "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                            "WHERE client_settings_id = ? AND category_id = ? AND group2product.id IS NULL")
                            .setParameter(1, groupId)
                            .setParameter(2, groupId)
                            .setParameter(3, clientId)
                            .setParameter(4, categoryId)
                            .executeUpdate();
                }
            });
        else
            execute(new Update() {
                @Override
                public void execute(Session session) {
                    session.createSQLQuery("DELETE group2product.* FROM product_entry " +
                            "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                            "WHERE client_settings_id = ? AND category_id = ? AND group2product.id IS NOT NULL")
                            .setParameter(1, groupId)
                            .setParameter(2, clientId)
                            .setParameter(3, categoryId)
                            .executeUpdate();
                }
            });
    }

    @Override
    public void merge(final int basic, final int source) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.createSQLQuery("INSERT INTO group2product ( `product_group_id`, `product_entry_id`) " +
                        "SELECT ?, `source`.product_entry_id FROM ( " +
                        " SELECT * FROM group2product " +
                        " WHERE product_group_id = ? " +
                        ") as `source` " +
                        "LEFT JOIN group2product as basic ON `source`.product_entry_id = basic.product_entry_id " +
                        "AND basic.product_group_id = ? " +
                        "WHERE basic.id IS NULL")
                        .setParameter(1, basic).setParameter(2, source).setParameter(3, basic).executeUpdate();
            }
        });
    }

    @Override
    public void difference(final int basic, final int source) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.createSQLQuery("DELETE basic.* FROM ( " +
                        " SELECT * FROM group2product " +
                        " WHERE product_group_id = ? " +
                        ") AS `source` " +
                        "JOIN group2product AS basic ON `source`.product_entry_id = basic.product_entry_id " +
                        "AND basic.product_group_id = ?").setParameter(1, source).setParameter(2, basic).executeUpdate();
            }
        });
    }

    @Override
    public void set(final String clientId, final int groupId, final List<String> productEntriesIds, boolean checked) {
        final String[] abc = new String[productEntriesIds.size()];
        Arrays.fill(abc, "?");

        if (checked){
            execute(new Update() {
                @Override
                public void execute(Session session) {
                    org.hibernate.Query query = session.createSQLQuery("INSERT INTO group2product (`product_group_id`, `product_entry_id`) " +
                            "SELECT ?, product_entry.id FROM product_entry " +
                            "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                            "WHERE client_settings_id = ? AND product_entry.id IN (" + Arrays.asList(abc).toString().replace("[", "").replace("]", "") + ") AND group2product.id IS NULL")
                            .setParameter(1, groupId)
                            .setParameter(2, groupId)
                            .setParameter(3, clientId);

                    int i = 4;
                    for (String entryId : productEntriesIds)
                        query.setParameter(i++, entryId);

                    query.executeUpdate();
                }
            });
        } else {
            execute(new Update() {
                @Override
                public void execute(Session session) {
                    org.hibernate.Query query = session.createSQLQuery("DELETE group2product.* FROM product_entry " +
                            "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                            "WHERE client_settings_id = ? AND product_entry.id IN (" + Arrays.asList(abc).toString().replace("[", "").replace("]", "") + ") AND group2product.id IS NOT NULL")
                            .setParameter(1, groupId)
                            .setParameter(2, clientId);

                    int i = 3;
                    for (String entryId : productEntriesIds)
                        query.setParameter(i++, entryId);

                    query.executeUpdate();
                }
            });
        }
    }
}
