package com.shopservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopservice.domain.Category;
import com.shopservice.domain.ProductEntry;
import com.shopservice.queries.CategoryQuery;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CategoriesTest {

    @Test
    public void testName() throws Exception {
        Util.matches(".*П.*", "Парта");

    }
}
