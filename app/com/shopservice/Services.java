package com.shopservice;

import com.shopservice.clientsinform.ClientsInformationProvider;
import com.shopservice.clientsinform.NativeClientInformationProvider;
import com.shopservice.domain.ClientSettings;

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

    private static HashMap<String, ClientsInformationProvider> clientsInformationProviders
                                                                    = new HashMap<String, ClientsInformationProvider>();

    public static final PriceListService priceListService = new PriceListService();

    public static final Queries queries = new Queries();

    public static DatabaseManager getDataBaseManager(String clientId) throws SQLException {
        if (!databaseManagers.containsKey(clientId))
            databaseManagers.put(clientId, new DatabaseManager(new StupidConnectionPool(ClientSettings.findById(clientId).databaseUrl)));

        return databaseManagers.get(clientId);
    }

    public static ClientsInformationProvider getClientsInformationProvider(String clientId)
    {
        if (!clientsInformationProviders.containsKey(clientId))
            clientsInformationProviders.put(clientId, new NativeClientInformationProvider(clientId));

        return clientsInformationProviders.get(clientId);
    }
}
