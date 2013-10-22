package com.shopservice.refreshers;

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
}
