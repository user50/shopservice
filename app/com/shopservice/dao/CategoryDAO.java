package com.shopservice.dao;

import com.shopservice.domain.Category;

import java.util.List;
import java.util.Set;

public interface CategoryDAO {
    List<Category> getCategories();

    Set<Category> getParents(Set<Category> categories);
}
