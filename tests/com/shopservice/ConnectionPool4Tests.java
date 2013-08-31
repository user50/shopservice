package com.shopservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool4Tests implements ConnectionPool {
    private final String URL_TO_TEST_DATABASE = "jdbc:mysql://127.0.0.1:3306/shopservice";
    private final String USER = "root";
    private final String PASSWORD = "root";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL_TO_TEST_DATABASE, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void releaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
