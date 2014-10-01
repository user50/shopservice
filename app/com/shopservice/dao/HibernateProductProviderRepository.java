package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import com.shopservice.domain.ProductProvider;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.shopservice.HibernateUtil.*;

public class HibernateProductProviderRepository implements ProductProviderRepository{
    @Override
    public List<ProductProvider> find(final String clientId) {
        return execute(new Query() {
            @Override
            public Object execute(Session session) {
                return session.createCriteria(ProductProvider.class, "provider")
                        .setFetchMode("provider.clientSettings", FetchMode.JOIN)
                        .createAlias("provider.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId)).list();
            }
        });
    }

    @Override
    public ProductProvider create(String clientId, final ProductProvider productProvider) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.save(productProvider);
            }
        });

        return productProvider;
    }

    @Override
    public ProductProvider update(final ProductProvider productProvider) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.update(productProvider);
            }
        });

        return productProvider;
    }

    @Override
    public void remove(final Integer providerId) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ProductProvider provider = (ProductProvider) session.get(ProductProvider.class, providerId);
                session.delete(provider);
            }
        });
    }

    @Override
    public ProductProvider find(final Integer providerId) {
        return execute(new Query() {
            @Override
            public Object execute(Session session) {
                return session.get(ProductProvider.class, providerId);
            }
        });
    }
}
