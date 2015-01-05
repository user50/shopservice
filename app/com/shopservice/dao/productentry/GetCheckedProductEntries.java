package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.dao.productentry.quesries.GetCheckedProductEntriesQuery;
import com.shopservice.domain.ProductEntry;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by Yevhen on 1/4/15.
 */
public class GetCheckedProductEntries implements ExternalCall<List<ProductEntry>, GetCheckedProductEntriesQuery> {
    @Override
    public List<ProductEntry> execute(final GetCheckedProductEntriesQuery query) {
        return HibernateUtil.execute(new HibernateUtil.Query() {
            @Override
            public List<ProductEntry> execute(Session session) {

                String sqlQuery = "SELECT product_entry.id, " +
                        "product_entry.product_id, product_entry.category_id, product_entry.custom_category_id, " +
                        "product_entry.description, product_entry.imageUrl, group2product.id IS NOT NULL AS checked FROM product_entry " +
                        "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = '" + query.getGroupId() + "' " +
                        "WHERE client_settings_id = '" + query.getClientId() + "' ";

                if (query.getCategoryId()!= null)
                    sqlQuery += " AND category_id = '" + query.getCategoryId() + "' ";

                if (query.getIds() != null && !query.getIds().isEmpty()) {
                    StringBuilder idsToQuery = new StringBuilder();
                    Iterator<String> iterator = query.getIds().iterator();

                    while (iterator.hasNext())
                        idsToQuery.append("'" + iterator.next() + "'").append(iterator.hasNext() ? "," : "");

                    sqlQuery += " AND product_entry.product_id IN(" + idsToQuery + " ) ";
                }

                if (query.getLimit() > -1)
                    sqlQuery += "'  LIMIT " + query.getLimit();

                if (query.getOffset() > -1)
                    sqlQuery += "  OFFSET " + query.getOffset() + " ";

                ScrollableResults results = session.createSQLQuery(sqlQuery).scroll();

                List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

                while (results.next()) {
                    ProductEntry productEntry = new ProductEntry();
                    productEntry.id = (String) results.get(0);
                    productEntry.productId = (String) results.get(1);
                    productEntry.categoryId = (String) results.get(2);
                    productEntry.customCategoryId = (String) results.get(3);
                    productEntry.description = (String) results.get(4);
                    productEntry.imageUrl = (String) results.get(5);
                    productEntry.checked = results.get(6).toString().equals("1");

                    productEntries.add(productEntry);
                }

                return productEntries;
            }
        });

    }
}
