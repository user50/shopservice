package com.shopservice.dao.productentry;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ExternalCall;
import com.shopservice.dao.productentry.quesries.GetProductEntriesQuery;
import com.shopservice.domain.ProductEntry;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shopservice.HibernateUtil.execute;

/**
 * Created by Yevhen on 1/4/15.
 */
public class GetProductEntries implements ExternalCall<Set<ProductEntry>, GetProductEntriesQuery> {
    @Override
    public Set<ProductEntry> execute(final GetProductEntriesQuery query) {
        return HibernateUtil.execute(new HibernateUtil.Query() {
            @Override
            public Set<ProductEntry> execute(Session session) {
                Criteria criteria = session.createCriteria(ProductEntry.class, "productEntry")
                        .setFetchMode("productEntry.clientSettings", FetchMode.JOIN)
                        .createAlias("productEntry.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", query.getClientId()));

                if (query.getCategoryId() != null)
                    criteria = criteria.add(Restrictions.eq("categoryId", query.getCategoryId()));

                return new HashSet<ProductEntry>((List<ProductEntry>) criteria.list());
            }
        });
    }
}
