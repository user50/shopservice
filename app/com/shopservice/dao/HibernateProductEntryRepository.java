package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductGroup;
import com.shopservice.transfer.Product;
import org.hibernate.FetchMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.*;

import static com.shopservice.HibernateUtil.*;

public class HibernateProductEntryRepository implements ProductEntryRepository {
    @Override
    public List<ProductEntry> findSelected(final String clientSettingsId, final int groupId, final boolean useCustomCategories) throws Exception {
        return execute(new Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                return session.createSQLQuery("SELECT product_entry.* FROM product_entry " +
                        "JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = :groupId " +
                        "WHERE client_settings_id = :clientSettingsId " + (useCustomCategories ? "AND custom_category_id IS NOT NULL" : "") ).addEntity(ProductEntry.class)
                        .setParameter("groupId", groupId)
                        .setParameter("clientSettingsId", clientSettingsId)
                        .list();
            }
        });
    }

    @Override
    public void add(final String clientsId, final Collection<ProductEntry> productEntries) throws Exception {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ClientSettings clientSettings = (ClientSettings) session.get(ClientSettings.class, clientsId);

                for (ProductEntry productEntry : productEntries)
                    productEntry.clientSettings = clientSettings;

                clientSettings.productEntries.addAll(productEntries);
                session.save(clientSettings);
            }
        });
    }

    @Override
    public void delete(final Collection<ProductEntry> productsToDelete) throws Exception {
        final List<String> ids = new ArrayList<>();
        for (ProductEntry productEntry : productsToDelete)
            ids.add(productEntry.id);

        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.createSQLQuery("DELETE FROM product_entry  WHERE product_entry.id IN (:values)")
                        .setParameter("values", ids ).executeUpdate();
            }
        });
    }

    @Override
    public List<ProductEntry> getWithChecked(final String clientId, final String categoryId, final int groupId) throws Exception {
        return execute(new Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                ScrollableResults results = session.createSQLQuery("SELECT product_entry.*, group2product.id IS NOT NULL AS checked FROM product_entry " +
                        "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = '" + groupId + "' " +
                        "WHERE client_settings_id = '" + clientId + "' AND category_id = '" + categoryId + "' ")
                        .scroll();

                List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

                while (results.next()){
                    ProductEntry productEntry = new ProductEntry();
                    productEntry.id = (String) results.get(0);
                    productEntry.productId = (String) results.get(2);
                    productEntry.categoryId = (String) results.get(3);
                    productEntry.customCategoryId = (String) results.get(4);
                    productEntry.description = (String) results.get(5);
                    productEntry.checked = results.get(6).toString().equals("1");

                    productEntries.add(productEntry);
                }

                return productEntries;
            }
        });
    }

    @Override
    public Map<String, Integer> getCountPerCategory(final String clientId, final String groupId) {
        return execute(new Query() {
            @Override
            public Map<String, Integer> execute(Session session) {
                ScrollableResults results = session.createSQLQuery("SELECT\n" +
                        "  category_id,\n" +
                        "  count(product_entry.id) AS total\n" +
                        "FROM product_entry\n" +
                        "  JOIN group2product ON group2product.product_entry_id = product_entry.id\n" +
                        "WHERE client_settings_id = '" + clientId + "' AND product_group_id = '" + groupId + "'\n" +
                        "GROUP BY category_id")
                        .scroll();

                Map<String, Integer> output = new HashMap<String, Integer>();

                while (results.next()) {
                    output.put((String)results.get(0), Integer.valueOf(results.get(1).toString()));
                }

                return output;
            }
        });
    }

    @Override
    public Set<ProductEntry> get(final String clientId, final String categoryId)
    {
        return execute(new Query() {
            @Override
            public Set<ProductEntry> execute(Session session) {
                return new HashSet<ProductEntry>((List<ProductEntry>) session.createCriteria(ProductEntry.class, "productEntry")
                        .setFetchMode("productEntry.clientSettings", FetchMode.JOIN)
                        .createAlias("productEntry.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId))
                        .add(Restrictions.eq("categoryId", categoryId))
                        .list()) ;
            }
        });
    }

    @Override
    public Set<ProductEntry> get(final String clientId) {
        return execute(new Query() {
            @Override
            public Set<ProductEntry> execute(Session session) {
                return new HashSet<ProductEntry>((List<ProductEntry>) session.createCriteria(ProductEntry.class, "productEntry")
                        .setFetchMode("productEntry.clientSettings", FetchMode.JOIN)
                        .createAlias("productEntry.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId))
                        .list()) ;
            }
        });
    }

    @Override
    public List<ProductEntry> get(final int groupId, final Collection<String> ids) {
        return execute(new Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                StringBuilder idsToQuery = new StringBuilder();
                Iterator<String> iterator = ids.iterator();

                while (iterator.hasNext())
                    idsToQuery.append("'"+iterator.next()+"'").append(iterator.hasNext() ? "," : "");

                if (ids.isEmpty())
                    idsToQuery.append("''");

                ScrollableResults results = session.createSQLQuery("SELECT\n" +
                        "  product_entry.id, product_entry.product_id, product_entry.category_id," +
                        " product_entry.custom_category_id, product_entry.description, \n" +
                        "  group2product.id IS NOT NULL AS checked\n" +
                        "FROM product_entry\n" +
                        "  LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = '"+groupId+"'\n" +
                        "WHERE product_entry.product_id IN(" + idsToQuery + " ) ").scroll();

                List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

                while (results.next()){
                    ProductEntry productEntry = new ProductEntry();
                    productEntry.id = (String) results.get(0);
                    productEntry.productId = (String) results.get(1);
                    productEntry.categoryId = (String) results.get(2);
                    productEntry.customCategoryId = (String) results.get(3);
                    productEntry.description = (String) results.get(4);
                    productEntry.checked = results.get(5).toString().equals("1");

                    productEntries.add(productEntry);
                }

                return productEntries;
            }
        });
    }

    @Override
    public List<ProductEntry> getWithCheckedPage(final String clientId, final String categoryId, final int groupId, final int offset, final int limit) {
        return execute(new Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                ScrollableResults results = session.createSQLQuery("SELECT product_entry.*, group2product.id IS NOT NULL AS checked FROM product_entry " +
                        "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = '"+groupId+"' " +
                        "WHERE client_settings_id = '"+clientId+"'  AND category_id = '"+categoryId+"'  LIMIT "+limit+"  OFFSET "+offset+" ")
                        .scroll();

                List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

                while (results.next()){
                    ProductEntry productEntry = new ProductEntry();
                    productEntry.id = (String) results.get(0);
                    productEntry.productId = (String) results.get(2);
                    productEntry.categoryId = (String) results.get(3);
                    productEntry.customCategoryId = (String) results.get(4);
                    productEntry.description = (String) results.get(5);
                    productEntry.checked = results.get(6).toString().equals("1");

                    productEntries.add(productEntry);
                }

                return productEntries;
            }
        });
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

    @Override
    public void update(final ProductEntry entry) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.update(entry);
            }
        });
    }
}
