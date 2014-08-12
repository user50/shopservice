package com.shopservice;

import org.junit.Test;

public class CategoriesTest {

    @Test
    public void testName() throws Exception {
        Util.matches(".*П.*", "Парта");

    }
}
