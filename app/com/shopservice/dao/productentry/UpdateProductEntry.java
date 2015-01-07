package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.domain.ProductEntry;
import org.hibernate.Session;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by user50 on 07.01.2015.
 */
public class UpdateProductEntry implements ExternalCall<Void, ProductEntry> {
    @Override
    public Void execute(final ProductEntry entry) {
        HibernateUtil.execute(new HibernateUtil.Update() {
            @Override
            public void execute(Session session) {
                session.update(entry);
            }
        });

        return null;
    }
}
