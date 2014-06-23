package com.shopservice.dao;

import com.shopservice.DatabaseManager;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.domain.Product;
import com.shopservice.queries.JdbcProductQuery;

import java.sql.SQLException;
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
    public List<Product> find(ProductConditions query) {
        try {
            return databaseManager.executeQueryForList(new JdbcProductQuery( clientId, query));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> find() {
        return find(new ProductConditions());
    }

    @Override
    public int size(ProductConditions conditions) {
        conditions.limit = null;
        conditions.offset = null;
        return find(conditions).size();
    }
}
