package com.shopservice.dao.productentry.quesries;

/**
 * Created by user50 on 07.01.2015.
 */
public class FindProductEntryQuery {
    private String clientId;
    private String clientsProductId;
    private String productEntryId;

    public FindProductEntryQuery(String clientId, String clientsProductId) {
        this.clientId = clientId;
        this.clientsProductId = clientsProductId;
    }

    public FindProductEntryQuery(String productEntryId) {
        this.productEntryId = productEntryId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientsProductId() {
        return clientsProductId;
    }

    public String getProductEntryId() {
        return productEntryId;
    }
}
