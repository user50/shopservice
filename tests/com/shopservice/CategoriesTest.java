package com.shopservice;

import com.shopservice.domain.Category;
import com.shopservice.queries.CategoryQuery;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class CategoriesTest {
    @Test
    public void testGetCategories() throws SQLException {
        List<Category> categories = Services.getDataBaseManager("client1").executeQueryForList(new CategoryQuery("client1"));
        System.out.println(categories.toString());
    }
}
