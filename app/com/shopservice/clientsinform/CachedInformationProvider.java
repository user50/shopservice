package com.shopservice.clientsinform;

import com.shopservice.domain.Category;
import com.shopservice.domain.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedInformationProvider implements ClientsInformationProvider {

    private List<Category> categories;
    private Map<String, Product> products = new HashMap<String, Product>();
    private Map<String, List<Product>> categoryToProducts = new HashMap<String, List<Product>>();

    private ClientsInformationProvider wrappedProvider;


    public CachedInformationProvider(ClientsInformationProvider wrappedProvider) {
        this.wrappedProvider = wrappedProvider;
    }

    @Override
    public List<Product> getProducts(String categoryId) {
        if (!categoryToProducts.containsKey(categoryId))
            categoryToProducts.put(categoryId, wrappedProvider.getProducts(categoryId));

        return categoryToProducts.get(categoryId);
    }

    @Override
    public List<Product> getProducts(Collection<String> productIds) {
        //no cached
        return wrappedProvider.getProducts(productIds);
    }

    @Override
    public List<Category> getCategories() {
        if (categories == null)
            categories = wrappedProvider.getCategories();

        return categories;
    }
}
