package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.productentry.*;
import com.shopservice.dao.productentry.quesries.*;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.ProductGroup;
import com.shopservice.transfer.Product;
import org.hibernate.FetchMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.*;

import static com.shopservice.HibernateUtil.*;

public class HibernateProductEntryRepository implements ProductEntryRepository {
    private FindSelectedProductEntry findSelectedProductEntry = new FindSelectedProductEntry();
    private AddProductEntries addProductEntries = new AddProductEntries();
    private DeleteProductEntries deleteProductEntries = new DeleteProductEntries();
    private GetCheckedProductEntries getCheckedProductEntries = new GetCheckedProductEntries();
    private GetCountPerCategory getCountPerCategory = new GetCountPerCategory();
    private GetProductEntries getProductEntries = new GetProductEntries();

    @Override
    public List<ProductEntry> findSelected(final String clientSettingsId, final int groupId, final boolean useCustomCategories) throws Exception {
        return findSelectedProductEntry.execute(new FindSelectedProductEntryQuery(clientSettingsId, groupId, useCustomCategories));
    }

    @Override
    public void add(final String clientsId, final Collection<ProductEntry> productEntries) throws Exception {
        addProductEntries.execute(new AddProductEntriesQuery(clientsId, productEntries));
    }

    @Override
    public void delete(final Collection<ProductEntry> productsToDelete) throws Exception {
        deleteProductEntries.execute(productsToDelete);
    }

    @Override
    public List<ProductEntry> getWithChecked(final String clientId, final String categoryId, final int groupId) throws Exception {
        return getCheckedProductEntries.execute(new GetCheckedProductEntriesQuery(clientId, categoryId, groupId));
    }

    @Override
    public Map<String, Integer> getCountPerCategory(final String clientId, final String groupId) {
        return getCountPerCategory.execute(new GetCountPerCategoryQuery(clientId, groupId));
    }

    @Override
    public Set<ProductEntry> get(final String clientId, final String categoryId)
    {
        return getProductEntries.execute(new GetProductEntriesQuery(clientId, categoryId));
    }

    @Override
    public Set<ProductEntry> get(final String clientId) {
        return getProductEntries.execute(new GetProductEntriesQuery(clientId));
    }

    @Override
    public List<ProductEntry> get(final String clientId, final int groupId, final Collection<String> ids) {
        return execute(new Query() {
            @Override
            public List<ProductEntry> execute(Session session) {
                StringBuilder idsToQuery = new StringBuilder();
                Iterator<String> iterator = ids.iterator();

                while (iterator.hasNext())
                    idsToQuery.append("'"+iterator.next()+"'").append(iterator.hasNext() ? "," : "");

                if (ids.isEmpty())
                    idsToQuery.append("''");

                ScrollableResults results = session.createSQLQuery("SELECT " +
                        " product_entry.id, product_entry.product_id, product_entry.category_id," +
                        " product_entry.custom_category_id, product_entry.description, product_entry.imageUrl, \n" +
                        "  group2product.id IS NOT NULL AS checked\n" +
                        "FROM product_entry\n" +
                        "  LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = '"+groupId+"'\n" +
                        "WHERE product_entry.product_id IN(" + idsToQuery + " )  and product_entry.client_settings_id = '"+clientId+"'").scroll();

                List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

                while (results.next()){
                    ProductEntry productEntry = new ProductEntry();
                    productEntry.id = (String) results.get(0);
                    productEntry.productId = (String) results.get(1);
                    productEntry.categoryId = (String) results.get(2);
                    productEntry.customCategoryId = (String) results.get(3);
                    productEntry.description = (String) results.get(4);
                    productEntry.imageUrl = (String) results.get(5);
                    productEntry.checked = results.get(6).toString().equals("1");

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
                ScrollableResults results = session.createSQLQuery("SELECT product_entry.id, " +
                        "product_entry.product_id, product_entry.category_id, product_entry.custom_category_id, " +
                        "product_entry.description, product_entry.imageUrl, group2product.id IS NOT NULL AS checked FROM product_entry " +
                        "LEFT JOIN group2product ON group2product.product_entry_id = product_entry.id AND group2product.product_group_id = '"+groupId+"' " +
                        "WHERE client_settings_id = '"+clientId+"'  AND category_id = '"+categoryId+"'  LIMIT "+limit+"  OFFSET "+offset+" ")
                        .scroll();

                List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

                while (results.next()){
                    ProductEntry productEntry = new ProductEntry();
                    productEntry.id = (String) results.get(0);
                    productEntry.productId = (String) results.get(1);
                    productEntry.categoryId = (String) results.get(2);
                    productEntry.customCategoryId = (String) results.get(3);
                    productEntry.description = (String) results.get(4);
                    productEntry.imageUrl = (String) results.get(5);
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
