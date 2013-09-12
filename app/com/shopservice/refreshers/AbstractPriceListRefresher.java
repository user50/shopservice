package com.shopservice.refreshers;

import com.shopservice.Services;
import com.shopservice.domain.ProductEntry;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPriceListRefresher implements PriceListRefresher {

    protected Set<String> getProductIds(String clientId) throws SQLException {
        Set<String> setOfProductIds = new HashSet<String>();

        for (ProductEntry productEntry : Services.productEntryService.getSelected(clientId))
            setOfProductIds.add(productEntry.productId);

        return setOfProductIds;
    }
}
