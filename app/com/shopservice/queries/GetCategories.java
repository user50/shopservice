package com.shopservice.queries;

import com.shopservice.Services;
import com.shopservice.domain.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCategories extends CategoryQuery {

    public GetCategories(String clientId) {
        super(clientId);
    }

    @Override
    public String getRawSql() {
        return Services.queries.getCategoriesQuery(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {

    }
}
