package com.shopservice.dao;

import com.shopservice.domain.Category;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlorangeCategoryRepository implements CategoryRepository {
    @Override
    public List<Category> getCategories() throws Exception {
        //TODO
        return null;
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        return new HashSet<>();
    }
}
