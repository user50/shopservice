package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.dao.productentry.quesries.FindSelectedProductEntryQuery;
import com.shopservice.domain.ProductEntry;
import org.hibernate.Session;

import java.util.List;


/**
 * Created by Yevhen on 1/4/15.
 */
public class FindSelectedProductEntry implements ExternalCall<List<ProductEntry>, FindSelectedProductEntryQuery> {
    @Override
    public List<ProductEntry> execute(final FindSelectedProductEntryQuery query) {
        return HibernateUtil.execute(new HibernateUtil.Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                return session.createSQLQuery("SELECT product_entry.* FROM product_entry " +
                        "JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = :groupId " +
                        "WHERE client_settings_id = :clientSettingsId " + (query.isUseCustomCategories() ? "AND custom_category_id IS NOT NULL" : "") ).addEntity(ProductEntry.class)
                        .setParameter("groupId", query.getGroupId())
                        .setParameter("clientSettingsId", query.getClientSettingsId())
                        .list();
            }
        });

    }
}
