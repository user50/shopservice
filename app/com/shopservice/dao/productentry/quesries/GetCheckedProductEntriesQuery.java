package com.shopservice.dao.productentry.quesries;

import java.util.Collection;

/**
 * Created by Yevhen on 1/4/15.
 */
public class GetCheckedProductEntriesQuery {
    private String clientId;
    private String categoryId;
    private int groupId;
    private Collection<String> ids;
    private int limit;
    private int offset;

    public GetCheckedProductEntriesQuery(String clientId, String categoryId, int groupId) {
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.groupId = groupId;
    }

    public GetCheckedProductEntriesQuery(String clientId, int groupId, Collection<String> ids) {
        this.clientId = clientId;
        this.groupId = groupId;
        this.ids = ids;
    }

    public GetCheckedProductEntriesQuery(String clientId, String categoryId, int groupId, int offset, int limit) {
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.groupId = groupId;
        this.offset = offset;
        this.limit = limit;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getGroupId() {
        return groupId;
    }

    public Collection<String> getIds() {
        return ids;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
