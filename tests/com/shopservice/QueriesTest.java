package com.shopservice;

import org.junit.Test;

public class QueriesTest {
    @Test
    public void testGetProductQueriesById(){
        Queries queries = new Queries();
        System.out.println(queries.getProductQueriesById("client1"));
    }
}