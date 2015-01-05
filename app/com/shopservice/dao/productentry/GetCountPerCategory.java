package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.dao.productentry.quesries.GetCheckedProductEntriesQuery;
import com.shopservice.dao.productentry.quesries.GetCountPerCategoryQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by Yevhen on 1/4/15.
 */
public class GetCountPerCategory implements ExternalCall<Map<String, Integer>, GetCountPerCategoryQuery> {
    @Override
    public Map<String, Integer> execute(final GetCountPerCategoryQuery query) {
        return HibernateUtil.execute(new HibernateUtil.Query() {
            @Override
            public Map<String, Integer> execute(Session session) {
                ScrollableResults results = session.createSQLQuery("SELECT\n" +
                        "  category_id,\n" +
                        "  count(product_entry.id) AS total\n" +
                        "FROM product_entry\n" +
                        "  JOIN group2product ON group2product.product_entry_id = product_entry.id\n" +
                        "WHERE client_settings_id = '" + query.getClientId() + "' AND product_group_id = '" + query.getGroupId() + "'\n" +
                        "GROUP BY category_id")
                        .scroll();

                Map<String, Integer> output = new HashMap<String, Integer>();

                while (results.next()) {
                    output.put((String) results.get(0), Integer.valueOf(results.get(1).toString()));
                }

                return output;
            }
        });

    }
}
