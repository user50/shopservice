package com.shopservice.dao;

import com.shopservice.Queries;
import com.shopservice.Root;
import com.shopservice.Util;

import java.util.ArrayList;

public class Test {
    @org.junit.Test
    public void testName() throws Exception {

        Root root = Util.fromXml(Queries.CONFIGURATION_FILE, Root.class);


    }
}
