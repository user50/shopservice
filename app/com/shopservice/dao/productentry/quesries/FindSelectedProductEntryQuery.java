package com.shopservice.dao.productentry.quesries;

/**
 * Created by Yevhen on 1/4/15.
 */
public class FindSelectedProductEntryQuery {
    private String clientSettingsId;
    private int groupId;
    private boolean useCustomCategories;

    public FindSelectedProductEntryQuery(String clientSettingsId, int groupId, boolean useCustomCategories) {
        this.clientSettingsId = clientSettingsId;
        this.groupId = groupId;
        this.useCustomCategories = useCustomCategories;
    }

    public String getClientSettingsId() {
        return clientSettingsId;
    }

    public int getGroupId() {
        return groupId;
    }

    public boolean isUseCustomCategories() {
        return useCustomCategories;
    }
}
