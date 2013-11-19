package com.shopservice.dao;

import com.shopservice.DatabaseManager;
import com.shopservice.Services;
import com.shopservice.domain.Category;
import com.shopservice.queries.CategoryQuery;
import com.shopservice.queries.GetCategories;
import com.shopservice.queries.GetParentCategories;
import com.shopservice.queries.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
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
    public List<Category> getCategories() throws SQLException {
        return databaseManager.executeQueryForList(new GetCategories(clientId));
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws SQLException {
        List<Category> parents = databaseManager.executeQueryForList(  new GetParentCategories( clientId, categoryIds   ) );

        return new HashSet<Category>(parents);
    }


}
