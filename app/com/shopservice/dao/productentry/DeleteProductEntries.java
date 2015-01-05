package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.domain.ProductEntry;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by Yevhen on 1/4/15.
 */
public class DeleteProductEntries implements ExternalCall<Void, Collection<ProductEntry>> {
    @Override
    public Void execute(Collection<ProductEntry> productsToDelete) {
        if (productsToDelete.isEmpty())
            return null;

        final List<String> ids = new ArrayList<>();
        for (ProductEntry productEntry : productsToDelete)
            ids.add(productEntry.id);

        HibernateUtil.execute(new HibernateUtil.Update() {
            @Override
            public void execute(Session session) {
                session.createSQLQuery("DELETE FROM product_entry  WHERE product_entry.id IN (:values)")
                        .setParameterList("values", ids).executeUpdate();
            }
        });

        return null;
    }
}
