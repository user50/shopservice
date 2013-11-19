package com.shopservice;

import com.shopservice.dao.*;
import com.shopservice.domain.ClientSettings;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class Services {

    private static HashMap<String, DatabaseManager> databaseManagers = new HashMap<String, DatabaseManager>();

    private static Map<String, CategoryRepository> categoryDAOs = new Hashtable<String, CategoryRepository>();
    private static Map<String, ProductRepository> productDAOs = new Hashtable<String, ProductRepository>();

    public static final PriceListService priceListService = new PriceListService();

    public static final Queries queries = new Queries();

    public static DatabaseManager getDataBaseManager(String clientId) throws SQLException {
        if (!databaseManagers.containsKey(clientId))
            databaseManagers.put(clientId, new DatabaseManager(new StupidConnectionPool(ClientSettings.findById(clientId).databaseUrl)));

        return databaseManagers.get(clientId);
    }

    public static CategoryRepository getCategoryDAO(String clientId)
    {
        if (!categoryDAOs.containsKey(clientId))
            categoryDAOs.put( clientId, new CachedCategoryRepository(new JdbcCategoryRepository(clientId)));

        return categoryDAOs.get(clientId);
    }

    public static ProductRepository getProductDAO(String clientId)
    {
        if (!productDAOs.containsKey(clientId))
            productDAOs.put( clientId, new CachedProductRepository(new JdbcProductRepository(clientId)));

        return productDAOs.get(clientId);
    }
}
