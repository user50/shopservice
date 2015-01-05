package com.shopservice.dao.productentry.quesries;

import com.shopservice.domain.ProductEntry;

import java.util.Collection;

/**
 * Created by Yevhen on 1/4/15.
 */
public class AddProductEntriesQuery {
    private String clientsId;
    private Collection<ProductEntry> productEntries;

    public AddProductEntriesQuery(String clientsId, Collection<ProductEntry> productEntries) {
        this.clientsId = clientsId;
        this.productEntries = productEntries;
    }

    public String getClientsId() {
        return clientsId;
    }

    public Collection<ProductEntry> getProductEntries() {
        return productEntries;
    }
}
