package com.shopservice;

import com.shopservice.queries.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseManager {

    private String dbUrl = "jdbc:mysql://localhost/domosed_backup?userName=...&password";

    public <T> List<T> executeQueryForList(Query<T> query) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String rawSql = query.getRawSql();
            preparedStatement = getConnection().prepareStatement(rawSql);
            query.prepare(getConnection().prepareStatement(rawSql));

            resultSet = preparedStatement.executeQuery();

            List<T> list = new ArrayList<T>();

            while (resultSet.next()){
                list.add(query.fill(resultSet));
            }

            return list;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public <T> T executeQueryForOne(Query<T> query) throws SQLException {
        List<T> list = executeQueryForList(query);

        if (list.size() > 1)
            throw new RuntimeException("Ambiguity during executing query.");

        return list.isEmpty()? null:list.get(0);
    }

    public Connection getConnection()
    {
        //TODO
        return null;
    }
}
