package com.shopservice.dao;

import com.shopservice.dao.productentry.*;
import com.shopservice.dao.productentry.quesries.*;
import com.shopservice.domain.ProductEntry;
import org.hibernate.FetchMode;
import org.hibernate.Session;
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
        return getCheckedProductEntries.execute(new GetCheckedProductEntriesQuery(clientId, groupId, ids));
    }

    @Override
    public List<ProductEntry> getWithCheckedPage(final String clientId, final String categoryId, final int groupId, final int offset, final int limit) {
        return getCheckedProductEntries.execute(new GetCheckedProductEntriesQuery(clientId, categoryId, groupId, offset, limit));
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
