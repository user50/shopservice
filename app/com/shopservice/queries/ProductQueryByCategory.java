package com.shopservice.queries;

import com.shopservice.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

//TODO this query allow to get clients's products by a category
public class ProductQueryByCategory extends ProductQuery {

    private String categoryId;

    protected ProductQueryByCategory(String clientId) {
        super(clientId);
    }

    public ProductQueryByCategory(String clientId, String categoryId) {
        super(clientId);
        this.categoryId = categoryId;
    }

    @Override
    public String getRawSql() {
        return Services.queries.getProductQueryByCategory(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setObject(1,categoryId);
    }
}
