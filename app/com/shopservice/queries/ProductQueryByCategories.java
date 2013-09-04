package com.shopservice.queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

//TODO this query allow to get clients's products by a collection of client's categories
public class ProductQueryByCategories extends ProductQuery {

    private Collection<String> categoryIds;

    protected ProductQueryByCategories(String clientId) {
        super(clientId);
    }

    public ProductQueryByCategories(String clientId, Collection<String> categoryIds) {
        super(clientId);
        this.categoryIds = categoryIds;
    }

    @Override
    public String getRawSql() {
        //TODO
        return null;
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        //TODO
    }
}
