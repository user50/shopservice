package com.shopservice;

import com.shopservice.assemblers.CategoryAssembler;
import com.shopservice.dao.*;
import com.shopservice.datasources.ApacheDataSourceProvider;
import com.shopservice.datasources.OneConnectionDataSourceProvider;
import com.shopservice.sync.ArtemSyncProduct;
import com.shopservice.sync.DefaultSyncProducts;
import com.shopservice.sync.SyncLocker;
import com.shopservice.sync.SyncProduct;
import com.shopservice.urlgenerate.UrlGenerator;
import com.shopservice.urlgenerate.UrlGeneratorStorage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.shopservice.MServiceInjector.injector;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class Services {

    private static ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    private static HashMap<String, DatabaseManager> databaseManagers = new HashMap<String, DatabaseManager>();

    private static Map<String, CategoryRepository> categoryDAOs = new Hashtable<String, CategoryRepository>();
    private static Map<String, SyncProduct> syncServices = new Hashtable<String, SyncProduct>();

    private static Map<String, ProductRepository> productDAOs = new Hashtable<String, ProductRepository>();

    public static final Queries queries = new Queries();

    private static final UrlGeneratorStorage URL_GENERATOR_STORAGE = UrlGeneratorStorage.getInstance();

    public static DatabaseManager getDataBaseManager(String clientId) throws SQLException {
        if (!databaseManagers.containsKey(clientId))
            databaseManagers.put(clientId, new DatabaseManager(new ApacheDataSourceProvider(clientSettingsRepository.get(clientId).databaseUrl, 5).provide()));

        return databaseManagers.get(clientId);
    }

    public static CategoryRepository getCategoryDAO(String clientId)
    {
        if (!categoryDAOs.containsKey(clientId)) {
            if (clientId.equals("artem"))
                categoryDAOs.put(clientId, new PersistCategoryByFile(new FlorangeCategoryRepository() ) );
            else if (clientId.equals("demo"))
                categoryDAOs.put(clientId, new DemoCategoryRepository());
            else
                categoryDAOs.put(clientId, new CachedCategoryRepository(new JdbcCategoryRepository(clientId)));
        }

        return categoryDAOs.get(clientId);
    }

    public static ProductRepository getProductDAO(String clientId)
    {
        if (!productDAOs.containsKey(clientId)) {
            if (clientId.equals("artem"))
                productDAOs.put(clientId, new MongoProductRepository("products"));
            else if (clientId.equals("demo"))
                productDAOs.put(clientId, new MongoProductRepository("demoProducts"));
            else
                productDAOs.put(clientId, new CachedProductRepository(new JdbcProductRepository(clientId)));
        }

        return productDAOs.get(clientId);
    }

    public static CategoryAssembler getCategoryAssembler(String clientId)
    {
        return new CategoryAssembler( injector.getInstance(ProductEntryRepository.class), getCategoryDAO(clientId) );
    }

    public static UrlGenerator getUrlGenerator(String clientId)
    {
        return URL_GENERATOR_STORAGE.get(clientId);
    }

    public static SyncProduct getSyncProduct(String clientId) {
        if (syncServices.containsKey(clientId))
            return syncServices.get(clientId);

        SyncProduct syncProduct;
        if (clientId.equals("artem"))
            syncProduct = new ArtemSyncProduct();
        else
            syncProduct = new DefaultSyncProducts(clientId);


        syncServices.put(clientId, new SyncLocker(syncProduct, clientId));

        return syncServices.get(clientId);
    }
}
