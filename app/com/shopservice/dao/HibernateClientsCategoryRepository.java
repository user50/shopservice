package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ClientsCategory;
import com.shopservice.domain.ProductGroup;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.shopservice.HibernateUtil.*;

public class HibernateClientsCategoryRepository implements ClientsCategoryRepository {
    @Override
    public List<ClientsCategory> get(final String clientId) {
        return execute(new Query() {
            @Override
            public List<ClientsCategory> execute(Session session) {
                return session.createCriteria(ClientsCategory.class, "category")
                        .setFetchMode("category.clientSettings", FetchMode.JOIN)
                        .createAlias("category.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId)).list();
            }
        });
    }

    @Override
    public ClientsCategory add(final String clientId, final ClientsCategory clientsCategory) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ClientSettings clientSettings = (ClientSettings) session.get(ClientSettings.class, clientId);
                clientsCategory.clientSettings = clientSettings;

                session.save(clientsCategory);
            }
        });

        return clientsCategory;
    }

    @Override
    public ClientsCategory update(final String clientId, final ClientsCategory clientsCategory) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ClientSettings clientSettings = (ClientSettings) session.get(ClientSettings.class, clientId);
                clientsCategory.clientSettings = clientSettings;

                session.update(clientsCategory);
            }
        });

        return clientsCategory;
    }

    @Override
    public void delete(final int clientsCategoryId) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ClientsCategory category = (ClientsCategory) session.load(ClientsCategory.class, clientsCategoryId);
                session.delete(category);
            }
        });
    }
}
