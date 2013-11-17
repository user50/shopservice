package com.shopservice.refreshers;

import com.google.common.collect.Sets;
import com.shopservice.Services;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractPriceListRefresher implements PriceListRefresher {

    protected Set<String> getProductIds(String clientId, int siteId) throws SQLException {
        Set<String> setOfProductIds = new HashSet<String>();

        for (ProductEntry productEntry : ProductEntry.findSelected(clientId, siteId))
            setOfProductIds.add(productEntry.productId);

        return setOfProductIds;
    }

    private Set<Category> getRelatedCategories(Set<Category> categories, String clientId)
    {
        Set<Category> parents = Services.getCategoryDAO(clientId).getParents(categories);

        if (parents.isEmpty())
            return parents;
        else
            return Sets.union(parents, getRelatedCategories(parents, clientId));
    }

    protected Set<Category> getCategories(Set<Category> categories, String clientId)
    {
        return Sets.union(categories, getRelatedCategories(categories, clientId));

    }
}
