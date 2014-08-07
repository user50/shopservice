package com.shopservice.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.shopservice.FileStorage;
import com.shopservice.MailService;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PersistCategoryByFile implements CategoryRepository {

    private static final String STORAGE_FILE = "florange;categories";

    private CategoryRepository repository;
    private FileStorage<List<Category>> storage;

    public PersistCategoryByFile(CategoryRepository repository) {
        this.repository = repository;
        storage = new FileStorage<List<Category>>(STORAGE_FILE) {
            @Override
            protected List<Category> construct(JsonNode node) {
                ArrayNode array = (ArrayNode)node;

                List<Category> persistedProducts = new ArrayList<>();
                for (int i=0;i<array.size(); i++)
                    persistedProducts.add(Json.fromJson(array.get(i), Category.class));

                return persistedProducts;
            }
        };
    }

    @Override
    public List<Category> getCategories() throws Exception {
        try {
            if (storage.isExist())
                return storage.get();

            List<Category> products = repository.getCategories();
            storage.save(products);

            return products;
        } catch (IOException e) {
            MailService.getInstance().report(e);

            return repository.getCategories();
        }
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        return repository.getParents(categoryIds);
    }
}