package com.shopservice.dao.productentry;

import com.shopservice.CollectionTransformer;
import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.dao.productentry.quesries.AddProductEntriesQuery;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by Yevhen on 1/4/15.
 */
public class AddProductEntries implements ExternalCall<Collection<ProductEntry>, AddProductEntriesQuery> {
    @Override
    public Collection<ProductEntry> execute(final AddProductEntriesQuery query) {
        HibernateUtil.execute(new HibernateUtil.Update() {
            @Override
            public void execute(Session session) {
                Transaction transaction = session.beginTransaction();

                ClientSettings clientSettings = (ClientSettings) session.get(ClientSettings.class, query.getClientsId());

                for (ProductEntry productEntry : query.getProductEntries())
                    productEntry.clientSettings = clientSettings;

                clientSettings.productEntries.addAll(query.getProductEntries());
                session.save(clientSettings);

                transaction.commit();
            }
        });

        return query.getProductEntries();
    }
}
