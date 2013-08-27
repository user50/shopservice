package com.shopservice;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class Services {

    public static final ClientSettings clientSettings = new ClientSettings();

    public static final PriceListService priceListService = new PriceListService();

    public static DatabaseManager getDataBaseManager(String clientId) {
        //todo
        return null;
    }
}
