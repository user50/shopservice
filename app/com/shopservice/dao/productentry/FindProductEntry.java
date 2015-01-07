package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.dao.productentry.quesries.FindProductEntryQuery;
import com.shopservice.domain.ProductEntry;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by user50 on 07.01.2015.
 */
public class FindProductEntry implements ExternalCall<ProductEntry, FindProductEntryQuery> {
    @Override
    public ProductEntry execute(final FindProductEntryQuery query) {
        if (query.getProductEntryId()==null){
            return HibernateUtil.execute(new HibernateUtil.Query() {
                @Override
                public ProductEntry execute(Session session) {
                    return (ProductEntry) session.createCriteria(ProductEntry.class, "productEntry")
                            .setFetchMode("productEntry.clientSettings", FetchMode.JOIN)
                            .createAlias("productEntry.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                            .add(Restrictions.eq("settings.id", query.getClientId()))
                            .add(Restrictions.eq("productId", query.getClientsProductId()))
                            .uniqueResult();
                }
            });
        } else
            return HibernateUtil.execute(new HibernateUtil.Query() {
                @Override
                public ProductEntry execute(Session session) {
                    return (ProductEntry) session.get(ProductEntry.class, query.getProductEntryId());
                }
            });
    }
}
