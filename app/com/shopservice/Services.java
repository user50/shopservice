package com.shopservice;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class Services {

    private static HashMap<String, DatabaseManager> databaseManagers = new HashMap<String, DatabaseManager>();

    public static final ClientSettingsService CLIENT_SETTINGS_SERVICE = new ClientSettingsService();

    public static final PriceListService priceListService = new PriceListService();

    public static final Queries queries = new Queries();

    public static DatabaseManager getDataBaseManager(String clientId) throws SQLException {
        if (!databaseManagers.containsKey(clientId))
            databaseManagers.put(clientId, new DatabaseManager(new StupidConnectionPool(CLIENT_SETTINGS_SERVICE.getDatabaseUrl(clientId))));

        return databaseManagers.get(clientId);
    }
}
