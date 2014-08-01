package com.shopservice.dao;

import com.shopservice.transfer.Category;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CategoryRepository {
    List<Category> getCategories() throws Exception;

    Set<Category> getParents(Collection<String> categoryIds) throws Exception;
}
