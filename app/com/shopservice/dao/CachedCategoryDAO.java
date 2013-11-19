package com.shopservice.dao;

import com.shopservice.domain.Category;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CachedCategoryDAO implements CategoryDAO {

    private CategoryDAO categoryDAO;
    private List<Category> categories;

    public CachedCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getCategories() throws Exception {
        if (categories == null)
            categories = categoryDAO.getCategories();

        return categories;
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        return categoryDAO.getParents(categoryIds);
    }
}
