package com.shopservice.dao;

import com.shopservice.domain.Category;

import java.util.List;

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
}
