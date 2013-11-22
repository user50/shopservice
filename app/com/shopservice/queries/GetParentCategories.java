package com.shopservice.queries;

import com.shopservice.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class GetParentCategories extends CategoryQuery {

    private Collection<String> categoryIds;

    public GetParentCategories(String clientId, Collection<String> categoryIds) {
        super(clientId);
        this.categoryIds = categoryIds;
    }

    @Override
    public String getRawSql() {
        if (categoryIds.isEmpty())
            return Services.queries.getProductQueryByListOfIds(clientId)
                    .replace("?", "null");

        String[] abc = new String[categoryIds.size()];
        Arrays.fill(abc, "?");

        return Services.queries.getQuery4GetParentCategories(clientId)
                .replace("?", Arrays.asList(abc).toString().replace("[","").replace("]","") );
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        int i = 0;

        for (String categoryId: categoryIds) {
            i+=1;
            statement.setObject( i, categoryId );
        }

    }
}
