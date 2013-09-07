package com.shopservice.queries;

import com.shopservice.Services;
import com.shopservice.domain.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO This query allow to get all client's categories
public class CategoryQuery implements Query<Category> {

    private String clientId;

    public CategoryQuery(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Category fill(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.id = resultSet.getString("categoryId");
        category.name = resultSet.getString("categoryName");

        return category;
    }

    @Override
    public String getRawSql() {
        return Services.queries.getCategoriesQuery(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {

    }
}
