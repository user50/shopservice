package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ClientsCategory;
import org.hibernate.FetchMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.*;

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

    @Override
    public List<ClientsCategory> getByParent(final Integer parentId) {
        return execute(new Query() {
            @Override
            public List<ClientsCategory> execute(Session session) {
                return session.createCriteria(ClientsCategory.class)
                        .add(Restrictions.eq("parentId", parentId)).list();
            }
        });
    }

    @Override
    public List<ClientsCategory> getParents(final Collection<Integer> categoryIds) {
        if (categoryIds.isEmpty())
            return new ArrayList<>();

        return execute(new Query() {
            @Override
            public List<ClientsCategory> execute(Session session) {
                return session.createQuery("select parent " +
                        "from com.shopservice.domain.ClientsCategory as parent, " +
                        "com.shopservice.domain.ClientsCategory as child\n" +
                        "WHERE parent.id = child.parentId and child.id in(:ids)").setParameterList("ids", categoryIds).list();
            }
        });
    }

    @Override
    public Map<String, ClientsCategory> getByProductIds(final String clientId, Collection<String> productIds) {
        return execute(new Query() {
            @Override
            public Object execute(Session session) {
                ScrollableResults results = session.createSQLQuery("SELECT product_entry.product_id, " +
                        "clients_category.id, clients_category.name, clients_category.parent_id, clients_category.client_settings_id " +
                        "FROM clients_category " +
                        "left join product_entry on clients_category.id = product_entry.custom_category_id " +
                        "where clients_category.client_settings_id = '" + clientId + "'").scroll();

                Map<String, ClientsCategory> output = new HashMap<>();

                while (results.next()){
                    ClientsCategory clientsCategory = new ClientsCategory();
                    clientsCategory.id = (Integer) results.get(1);
                    clientsCategory.name = (String) results.get(2);
                    clientsCategory.parentId = (Integer) results.get(3);

                    String productId = (String) results.get(0);

                    output.put(productId, clientsCategory);
                }

                return output;
            }
        });
    }
}
