package com.shopservice.queries;

import com.shopservice.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by user50 on 21.06.2014.
 */
public class GetProductsByWords extends ProductQuery {

    private Collection<String> words;

    public GetProductsByWords(String clientId, Collection<String> words) {
        super(clientId);
        this.words = words;
    }

    @Override
    public String getRawSql() {
        String query = Services.queries.getProductQueryByWords(clientId);
        query += " WHERE";

        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext())
            query += " products_name LIKE ? " + (iterator.hasNext() ? "AND" : "");

        return Services.queries.getProductQueryByWords(clientId);
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        int i = 1;
        for (String word : words)
            statement.setString(i++, "%"+word+"%");
    }
}
