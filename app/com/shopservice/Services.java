package com.shopservice;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class Services {

    public static final ClientSettingsService CLIENT_SETTINGS_SERVICE = new ClientSettingsService();

    public static final PriceListService priceListService = new PriceListService();

    public static final Queries queries = new Queries();

    public static DatabaseManager getDataBaseManager(String clientId) {
        //todo
        return null;
    }
}
