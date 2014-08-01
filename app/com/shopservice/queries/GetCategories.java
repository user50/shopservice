package com.shopservice.queries;

import com.shopservice.Queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetCategories extends CategoryQuery {

    public GetCategories(String clientId) {
        super(clientId);
    }

    @Override
    public String getRawSql() {
        return Queries.getInstance().getCategoriesQuery(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {

    }
}
