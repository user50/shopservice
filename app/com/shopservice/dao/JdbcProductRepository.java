package com.shopservice.dao;

import com.shopservice.DatabaseManager;
import com.shopservice.Services;
import com.shopservice.domain.Product;
import com.shopservice.queries.GetProductsByCategory;
import com.shopservice.queries.GetProductsByIds;
import com.shopservice.queries.GetProductsByWords;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JdbcProductRepository implements ProductRepository {

    private DatabaseManager databaseManager;
    private String clientId;

    public JdbcProductRepository(String clientId) {
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
            return  databaseManager.executeQueryForList(new GetProductsByCategory(clientId, categoryId));
        } catch (SQLException e) {
            //todo log
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getProducts(Collection<String> productId) {
        try {
            return databaseManager.executeQueryForList(new GetProductsByIds(clientId, new ArrayList<String>( productId )));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findProductsByWords(List<String> words) {
        try {
            return  databaseManager.executeQueryForList(new GetProductsByWords(clientId, words));
        } catch (SQLException e) {
            //todo log
            throw new RuntimeException(e);
        }
    }

}
