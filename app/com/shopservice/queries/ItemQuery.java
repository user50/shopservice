package com.shopservice.queries;

import com.shopservice.pricelist.models.price.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class ItemQuery implements Query<Item> {
    protected String clientId;

    protected ItemQuery(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Item fill(ResultSet resultSet) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
