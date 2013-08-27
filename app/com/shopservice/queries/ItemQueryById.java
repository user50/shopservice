package com.shopservice.queries;

import com.shopservice.queries.ItemQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class ItemQueryById extends ItemQuery {
    private String productId;

    protected ItemQueryById(String clientId) {
        super(clientId);
    }

    public ItemQueryById(String clientId, String productId) {
        super(clientId);
        this.productId = productId;
    }

    @Override
    public String getRawSql() {
        return getQuery(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setObject(1, productId);
    }

    private String getQuery(String clientId)
    {
        return null;//TODO
    }
}
