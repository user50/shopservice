package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductGroup;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.shopservice.HibernateUtil.*;

public class HibernateProductEntryRepository implements ProductEntryRepository {
    @Override
    public List<ProductEntry> findSelected(final String clientSettingsId, final int groupId) throws Exception {
        return execute(new Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                return session.createSQLQuery("SELECT product_entry.* FROM product_entry " +
                        "JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = ? " +
                        "WHERE client_settings_id = ?").addEntity(ProductEntry.class).setParameter(1, groupId).setParameter(2, clientSettingsId).list();
            }
        });
    }

    @Override
    public void add(final String clientsId, final Collection<ProductEntry> productEntries) throws Exception {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ClientSettings clientSettings = (ClientSettings) session.get(ClientSettings.class, clientsId);
                clientSettings.productEntries.addAll(productEntries);
                session.save(clientSettings);
            }
        });
    }

    @Override
    public void delete(Collection<ProductEntry> productsToDelete) throws Exception {
        execute(new Update() {
            @Override
            public void execute(Session session) {

            }
        });
    }

    @Override
    public List<ProductEntry> getWithChecked(String clientId, String categoryId, int groupId) throws Exception {
        return null;
    }

    @Override
    public Map<String, Integer> getCountPerCategory(String clientId, String groupId) {
        return null;
    }

    @Override
    public Set<ProductEntry> get(String clientId, String categoryId) {
        return null;
    }

    @Override
    public Set<ProductEntry> get(String clientId) {
        return null;
    }

    @Override
    public List<ProductEntry> get(int groupId, Collection<String> ids) {
        return null;
    }

    @Override
    public List<ProductEntry> getWithCheckedPage(String clientId, String categoryId, int groupId, int offset, int limit) {
        return null;
    }

    @Override
    public ProductEntry find(final String productEntryId) {
        return execute(new Query() {
            @Override
            public ProductEntry execute(Session session) {
                return (ProductEntry) session.get(ProductEntry.class, productEntryId);
            }
        });
    }

    @Override
    public ProductEntry find(final String clientId, final String clientsProductId) {
        return execute(new Query() {
            @Override
            public ProductEntry execute(Session session) {
                return (ProductEntry) session.createCriteria(ProductEntry.class, "productEntry")
                        .setFetchMode("productEntry.clientSettings", FetchMode.JOIN)
                        .createAlias("productEntry.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId))
                        .add(Restrictions.eq("clientsProductId", clientsProductId))
                        .uniqueResult();
            }
        });
    }
}
