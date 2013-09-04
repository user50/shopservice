package com.shopservice.queries;

import com.shopservice.domain.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO This query allow to get all client's categories
public class CategoryQuery implements Query<Category> {
    @Override
    public Category fill(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.id = resultSet.getString("categoryId");
        category.name = resultSet.getString("categoryName");

        return category;
    }

    @Override
    public String getRawSql() {
        return null;
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {

    }
}
