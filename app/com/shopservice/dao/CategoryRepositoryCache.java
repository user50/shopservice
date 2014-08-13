package com.shopservice.dao;


import com.shopservice.transfer.Category;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by user50 on 09.08.2014.
 */
public class CategoryRepositoryCache implements CategoryRepository {

    private CategoryRepository repository;

    @Override
    public List<Category> getCategories() throws Exception {
        return null;
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        return null;
    }
}
