package com.shopservice.queries;

import com.shopservice.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class ProductQueryById extends ProductQuery {
    private String productId;

    protected ProductQueryById(String clientId) {
        super(clientId);
    }

    public ProductQueryById(String clientId, String productId) {
        super(clientId);
        this.productId = productId;
    }

    @Override
    public String getRawSql() {
        return Services.queries.getProductQueriesById(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setObject(1, productId);
    }
}
