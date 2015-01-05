package com.shopservice.dao.productentry.quesries;

/**
 * Created by Yevhen on 1/4/15.
 */
public class GetCountPerCategoryQuery {
    private String clientId;
    private String groupId;

    public GetCountPerCategoryQuery(String clientId, String groupId) {
        this.clientId = clientId;
        this.groupId = groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getGroupId() {
        return groupId;
    }
}
