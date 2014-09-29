package com.shopservice.refreshers;

import com.google.common.collect.Sets;
import com.shopservice.MServiceInjector;
import com.shopservice.MailService;
import com.shopservice.Services;
import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.dao.ClientsCategoryRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.domain.ClientsCategory;
import com.shopservice.transfer.Category;
import com.shopservice.domain.ProductEntry;
import play.Logger;

import java.util.*;

import static com.shopservice.MServiceInjector.injector;

public abstract class AbstractPriceListRefresher implements PriceListRefresher {

    protected ProductEntryRepository productEntryRepository = injector.getInstance(ProductEntryRepository.class);
    protected ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    protected Set<String> getProductIds(String clientId, int groupId, boolean useCustomCategories) throws Exception {
        Set<String> setOfProductIds = new HashSet<String>();

        for (ProductEntry productEntry : productEntryRepository.findSelected(clientId, groupId, useCustomCategories))
            setOfProductIds.add(productEntry.productId);

        if (setOfProductIds.isEmpty())
            setOfProductIds.add("nonExistingProductId"); //crutch

        return setOfProductIds;
    }

    private Set<Category> getRelatedCategories(Set<Category> categories, String clientId, CategorySource categorySource ) throws Exception {
        Set<Category> parents = categorySource.getParents(getCategoryIds(categories));

        if (parents.isEmpty())
            return parents;
        else
            return Sets.union(parents, getRelatedCategories(parents, clientId, categorySource));
    }

    protected Set<Category> getCategories(Set<Category> categories, String clientId, CategorySource categorySource ) throws Exception {
        return Sets.union(categories, getRelatedCategories(categories, clientId, categorySource));

    }

    private Collection<String> getCategoryIds(Set<Category> categories)
    {
        Collection<String> ids = new ArrayList<String>();
        for (Category category : categories)
            ids.add(category.id);

        return ids;
    }

    protected static interface CategorySource
    {
        Set<Category> getParents(Collection<String> categoryIds);
    }

    protected static class DefaultCategorySource implements CategorySource
    {
        private String clientId;

        public DefaultCategorySource(String clientId) {
            this.clientId = clientId;
        }

        @Override
        public Set<Category> getParents(Collection<String> categoryIds) {
            try {
                return Services.getCategoryDAO(clientId).getParents(categoryIds);
            } catch (Exception e) {
                Logger.error("Unable to retrieve parent's categories from client's database", e);
                MailService.getInstance().report(e);
            }

            return new HashSet<>();
        }
    }

    protected static class CustomCategorySource implements CategorySource
    {
        ClientsCategoryRepository repository = MServiceInjector.injector.getInstance(ClientsCategoryRepository.class);

        @Override
        public Set<Category> getParents(Collection<String> categoryIds) {
            List<Integer> ids = new ArrayList<>();
            for (String categoryId : categoryIds)
                ids.add(Integer.valueOf(categoryId));

            Set<Category> categories = new HashSet<>();
            for (ClientsCategory clientsCategory : repository.getParents(ids)) {
                Category category = new Category(clientsCategory.id.toString());
                category.name = clientsCategory.name;
                category.parentId = clientsCategory.parentId.toString();
            }

            return categories;
        }
    }
}
