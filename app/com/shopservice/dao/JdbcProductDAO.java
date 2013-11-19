package com.shopservice.dao;

import com.shopservice.DatabaseManager;
import com.shopservice.Services;
import com.shopservice.domain.Product;
import com.shopservice.queries.GetProductsByCategory;
import com.shopservice.queries.GetProductsByIds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JdbcProductDAO implements ProductDAO{

    private DatabaseManager databaseManager;
    private String clientId;

    public JdbcProductDAO(String clientId) {
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
}
