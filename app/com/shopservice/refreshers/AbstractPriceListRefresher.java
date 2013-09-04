package com.shopservice.refreshers;

import com.shopservice.Services;
import com.shopservice.domain.CategoryEntry;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;
import com.shopservice.queries.ProductQueryByCategories;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPriceListRefresher implements PriceListRefresher {

    protected Set<String> getProductIds(String clientId) throws SQLException {
        Set<String> setOfProductIds = new HashSet<String>();

        for (ProductEntry productEntry : ProductEntry.find(clientId))
            setOfProductIds.add(productEntry.productId);

        Set<String> setOfCategoryIds = new HashSet<String>();
        for (CategoryEntry categoryEntry : CategoryEntry.find(clientId))
            setOfCategoryIds.add( categoryEntry.categoryId );

        for (Product product : Services.getDataBaseManager(clientId).executeQueryForList(new ProductQueryByCategories(clientId, setOfCategoryIds)))
            setOfProductIds.add(product.id);

        return setOfCategoryIds;
    }
}
