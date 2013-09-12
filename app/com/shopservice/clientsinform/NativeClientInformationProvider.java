package com.shopservice.clientsinform;

import com.shopservice.DatabaseManager;
import com.shopservice.Services;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;
import com.shopservice.queries.CategoryQuery;
import com.shopservice.queries.ProductQueryByCategory;
import com.shopservice.queries.ProductQueryById;

import java.sql.SQLException;
import java.util.List;

public class NativeClientInformationProvider implements ClientsInformationProvider {

    private DatabaseManager databaseManager;
    private String clientId;

    public NativeClientInformationProvider(String clientId) {
        this.clientId = clientId;
        try {
            databaseManager = Services.getDataBaseManager(clientId);
        } catch (SQLException e) {
            //todo log
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getProducts(String categoryId) {
        try {
            return  databaseManager.executeQueryForList(new ProductQueryByCategory(clientId, categoryId));
        } catch (SQLException e) {
            //todo log
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProduct(String productId) {
        try {
            return databaseManager.executeQueryForOne(new ProductQueryById(clientId, productId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> getCategories() {
        try {
            return databaseManager.executeQueryForList(new CategoryQuery(clientId));
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }
}
