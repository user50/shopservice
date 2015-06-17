package com.shopservice;

import com.shopservice.queries.Query;
import com.shopservice.queries.Update;
import play.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseManager {

    private DataSource dataSource;

    public DatabaseManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> executeQueryForList(Query<T> query) throws SQLException {

        long start = System.currentTimeMillis();
        Connection connection = dataSource.getConnection();
        Logger.info("Get connection "+(System.currentTimeMillis() - start));

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String rawSql = query.getRawSql();
            preparedStatement = connection.prepareStatement(rawSql);
            query.prepare(preparedStatement);

            start = System.currentTimeMillis();
            resultSet = preparedStatement.executeQuery();
            Logger.info("Execute Query "+ preparedStatement.toString());
            Logger.info("Execute Query "+(System.currentTimeMillis() - start));

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

            connection.close();
        }
    }

    public <T> Set<T> executeQueryForSet(Query<T> query) throws SQLException {

        long start = System.currentTimeMillis();
        Connection connection = dataSource.getConnection();
        Logger.info("Get connection "+(System.currentTimeMillis() - start));

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String rawSql = query.getRawSql();
            preparedStatement = connection.prepareStatement(rawSql);
            query.prepare(preparedStatement);

            start = System.currentTimeMillis();
            resultSet = preparedStatement.executeQuery();
            Logger.info("Execute Query "+ preparedStatement.toString());
            Logger.info("Execute Query "+(System.currentTimeMillis() - start));

            Set<T> set = new HashSet<>();

            while (resultSet.next()){
                set.add(query.fill(resultSet));
            }

            return set;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            connection.close();
        }
    }

    public <T> T executeQueryForOne(Query<T> query) throws SQLException {
        List<T> list = executeQueryForList(query);

        if (list.size() > 1)
            throw new RuntimeException("Ambiguity during executing query.");

        if (list.size() == 1 && list.get(0) == null)
            return null;

        return list.isEmpty()? null:list.get(0);
    }

    public int executeUpdate(Update update) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String rawSql = update.getRawSql();
            preparedStatement = connection.prepareStatement(rawSql);
            update.prepare(preparedStatement);

            return preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            connection.close();
        }
    }
}
