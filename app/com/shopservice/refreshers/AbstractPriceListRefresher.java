package com.shopservice.refreshers;

import com.google.common.collect.Sets;
import com.shopservice.Services;
import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.domain.Category;
import com.shopservice.domain.ProductEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.shopservice.MServiceInjector.injector;

public abstract class AbstractPriceListRefresher implements PriceListRefresher {

    protected ProductEntryRepository productEntryRepository = injector.getInstance(ProductEntryRepository.class);
    protected ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    protected Set<String> getProductIds(String clientId, int siteId) throws Exception {
        Set<String> setOfProductIds = new HashSet<String>();

        for (ProductEntry productEntry : productEntryRepository.findSelected(clientId, siteId))
            setOfProductIds.add(productEntry.productId);

        if (setOfProductIds.isEmpty())
            setOfProductIds.add("nonExistingProductId"); //crutch

        return setOfProductIds;
    }

    private Set<Category> getRelatedCategories(Set<Category> categories, String clientId) throws Exception {
        Set<Category> parents = Services.getCategoryDAO(clientId).getParents(getCategoryIds(categories));

        if (parents.isEmpty())
            return parents;
        else
            return Sets.union(parents, getRelatedCategories(parents, clientId));
    }

    protected Set<Category> getCategories(Set<Category> categories, String clientId) throws Exception {
        return Sets.union(categories, getRelatedCategories(categories, clientId));

    }

    private Collection<String> getCategoryIds(Set<Category> categories)
    {
        Collection<String> ids = new ArrayList<String>();
        for (Category category : categories)
            ids.add(category.id);

        return ids;
    }
}
