package com.shopservice.dao;

import com.shopservice.transfer.Category;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CachedCategoryRepository implements CategoryRepository {

    private CategoryRepository categoryRepository;
    private List<Category> categories;

    public CachedCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() throws Exception {
        if (categories == null)
            categories = categoryRepository.getCategories();
        else
            refreshCache();

        return categories;
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        return categoryRepository.getParents(categoryIds);
    }

    private void refreshCache() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    categories = categoryRepository.getCategories();
                } catch (Exception e) {
                    //need to log this exception
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
