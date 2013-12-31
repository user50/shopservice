package com.shopservice.com.shopservice;

import com.shopservice.HikariConnectionPool;
import org.junit.Test;

public class HikariConnectionPoolTest {

    @Test
    public void testWrongUrl() throws Exception {
        HikariConnectionPool connectionPool = new HikariConnectionPool("jdbc:mysql://localhost:3306/shopservice?user=root&password=neuser5");
        connectionPool.getConnection();

    }
}
