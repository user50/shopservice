package com.shopservice.refreshers;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public interface PriceListRefresher {
    public byte[] generate(String clientId, int siteId, boolean useCustomCategories) throws Exception;
}
