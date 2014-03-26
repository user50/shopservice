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
        ObjectMapper objectMapper = new ObjectMapper();

        List<ProductEntry> productEntries = objectMapper.readValue("[{\"id\":\"1\"}]", new TypeReference<List<ProductEntry>>() { });

    }
}
