package com.shopservice.dao;

import com.shopservice.DatabaseManager;
import com.shopservice.HikariConnectionPool;
import com.shopservice.transfer.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class JdbcCategoryRepositoryTest {

    JdbcCategoryRepository repository;

    public JdbcCategoryRepositoryTest(String clientId, String url) {
        repository = new JdbcCategoryRepository(new DatabaseManager(new HikariConnectionPool(url)), clientId );
    }

    @Test
    public void testGetCategories() throws Exception {
        List<Category> categories = repository.getCategories();

        assertThat(categories).isNotEmpty();
    }

    @Test
    public void testGetParentCategories() throws Exception {
        List<String> categoryIds = extractCategoryIds(repository.getCategories());

        List<String> depthOne = extractCategoryIds( repository.getParents(categoryIds) );
        List<String> depthTwo = extractCategoryIds( repository.getParents(depthOne) );
        List<String> depthThree = extractCategoryIds( repository.getParents(depthTwo) );
        List<String> depthFour = extractCategoryIds( repository.getParents(depthThree) );

        assertThat(depthFour).isEmpty();
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        List<Object[]> cases = new ArrayList<Object[]>();
        cases.add( new Object[]{"client1", "jdbc:mysql://91.200.40.60/newdomosed1?user=newdomosed1&password=kissme22"} );
        cases.add( new Object[]{"client2", "jdbc:mysql://91.240.20.7:3306/gneus153_mebel?user=gneus153_eugene&password=23111989kjpjd"} );

        return cases;
    }

    private List<String> extractCategoryIds(Collection<Category> categories) throws SQLException {
        List<String> categoriesIds = new ArrayList<String>();
        for (Category category : categories)
            categoriesIds.add(category.id);

        return categoriesIds;
    }
}
