package com.shopservice;

import com.shopservice.domain.Category;
import com.shopservice.queries.CategoryQuery;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class CategoriesTest {
    @Test
    public void testGetCategories() throws SQLException {
        DatabaseManager manager = new DatabaseManager(new ConnectionPool4Tests());
        List<Category> categories = manager.executeQueryForList(new CategoryQuery("client1"));
        for (Category category: categories)
        System.out.println(category.toString());
    }
}
