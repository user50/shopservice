package com.shopservice.queries;

import com.shopservice.transfer.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CategoryQuery implements Query<Category> {

    protected String clientId;

    protected CategoryQuery(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Category fill(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.id = resultSet.getString("categoryId");
        category.name = resultSet.getString("categoryName");
        String parentId = resultSet.getString("parentId");
        category.parentId = parentId.equals("0") ? null : parentId;

        return category;
    }
}
