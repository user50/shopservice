package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import org.hibernate.Session;

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
    public ProductEntry find(String productEntryId) {
        return null;
    }

    @Override
    public ProductEntry find(String clientId, String clientsProductId) {
        return null;
    }
}
