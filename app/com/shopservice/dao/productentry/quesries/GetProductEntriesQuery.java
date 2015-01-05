package com.shopservice.dao.productentry.quesries;

/**
 * Created by Yevhen on 1/4/15.
 */
public class GetProductEntriesQuery {
    private String clientId;
    private String categoryId;

    public GetProductEntriesQuery(String clientId) {
        this.clientId = clientId;
    }

    public GetProductEntriesQuery(String clientId, String categoryId) {
        this.clientId = clientId;
        this.categoryId = categoryId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
