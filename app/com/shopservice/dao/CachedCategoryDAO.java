package com.shopservice.dao;

import com.shopservice.domain.Category;

import java.util.List;
import java.util.Set;

public class CachedCategoryDAO implements CategoryDAO {

    private CategoryDAO categoryDAO;
    private List<Category> categories;

    public CachedCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getCategories() {
        if (categories == null)
            categories = categoryDAO.getCategories();

        return categories;
    }

    @Override
    public Set<Category> getParents(Set<Category> categories) {
        return categoryDAO.getParents(categories);
    }
}
