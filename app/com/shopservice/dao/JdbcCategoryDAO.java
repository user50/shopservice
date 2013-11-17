package com.shopservice.dao;

import com.shopservice.DatabaseManager;
import com.shopservice.Services;
import com.shopservice.domain.Category;
import com.shopservice.queries.CategoryQuery;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcCategoryDAO implements CategoryDAO {


    private DatabaseManager databaseManager;
    private String clientId;

    public JdbcCategoryDAO(String clientId) {
        this.clientId = clientId;
        try {
            databaseManager = Services.getDataBaseManager(clientId);
        } catch (SQLException e) {
            //todo log
            e.printStackTrace();
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

    @Override
    public Set<Category> getParents(Set<Category> categories) {
        //TODO
        return new HashSet<>();
    }


}
