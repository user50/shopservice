package com.shopservice.refreshers;

import com.google.common.collect.Sets;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.ClientsCategoryRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientsCategory;
import com.shopservice.domain.ProductEntry;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;

import java.util.*;

import static com.shopservice.MServiceInjector.injector;

/**
 * Created by user50 on 05.10.2014.
 */
public class DefaultDataSource implements PriceListGenerator.DataSource {

    protected ProductEntryRepository productEntryRepository = injector.getInstance(ProductEntryRepository.class);
    protected ProductGroupRepository productGroupRepository = injector.getInstance(ProductGroupRepository.class);
    protected ClientsCategoryRepository clientsCategoryRepository = injector.getInstance(ClientsCategoryRepository.class);

    private String clientId;
    private String groupId;

    private Map<String, Category> categories = new HashMap<>();

    public DefaultDataSource(String clientId, String groupId) {
        this.clientId = clientId;
        this.groupId = groupId;
    }

    @Override
    public List<Product> getProducts() throws Exception {
        boolean useCustomCategories = productGroupRepository.get(Long.valueOf(groupId)).useCustomCategories;
        List<ProductEntry> productEntries = productEntryRepository.findSelected(clientId, Integer.valueOf(groupId),useCustomCategories);

        Map<String, ProductEntry> productMap = new HashMap<>();

        for (ProductEntry productEntry : productEntries) {
            productMap.put(productEntry.productId, productEntry);
        }

        ProductConditions productConditions = new ProductConditions();

        for (ProductEntry productEntry : productEntries)
            productConditions.productIds.add(productEntry.productId);

        List<Product> products = Services.getProductDAO(clientId).find(productConditions);

        if (useCustomCategories)
            return getProductsForCustomCategories(products, productConditions, productMap);
        else
            return getProductsForOriginalCategories(products, productConditions, productMap);

    }

    private List<Product> getProductsForOriginalCategories(List<Product> products, ProductConditions productConditions, Map<String, ProductEntry> productMap) {
        categories = new HashMap<>();
        for (Product product : products) {
            categories.put(product.category.id, product.category);
            if(productMap.get(product.id).description != null)
                product.description = productMap.get(product.id).description;
        }

        return products;
    }

    private List<Product> getProductsForCustomCategories(List<Product> products, ProductConditions productConditions, Map<String, ProductEntry> productMap) {
        Map<String, Category> categories = getCategories(clientId, productConditions.productIds);

        for (Product product : products) {
            product.category = categories.get(product.id);
            if(productMap.get(product.id).description != null)
                product.description = productMap.get(product.id).description;
        }

        return products;
    }

    private Map<String,Category> getCategories(String clientId, Collection<String> productIds)
    {
        categories = new HashMap<>();

        Map<String, ClientsCategory> productIdToClientsCategory =
                clientsCategoryRepository.getByProductIds(clientId, productIds);

        for (Map.Entry<String, ClientsCategory> entry : productIdToClientsCategory.entrySet()) {
            Category category = new Category(entry.getValue().id.toString());
            category.name = entry.getValue().name;

            if (entry.getValue().parentId != null)
                category.parentId = entry.getValue().parentId.toString();

            categories.put(entry.getKey(), category);
        }

        return categories;
    }

    @Override
    public Set<Category> getCategories() throws Exception {

        PriceListGenerator.CategorySource categorySource = productGroupRepository.get(Long.valueOf(groupId)).useCustomCategories
                ? new PriceListGenerator.CustomCategorySource()
                : new PriceListGenerator.DefaultCategorySource(clientId);

        return Sets.union(new HashSet<Category>(categories.values()), getRelatedCategories(new HashSet<Category>(categories.values()), clientId, categorySource));
    }

    private Set<Category> getRelatedCategories(Set<Category> categories, String clientId, PriceListGenerator.CategorySource categorySource ) throws Exception {
        Set<Category> parents = categorySource.getParents(getCategoryIds(categories));

        if (parents.isEmpty())
            return parents;
        else
            return Sets.union(parents, getRelatedCategories(parents, clientId, categorySource));
    }

    private Collection<String> getCategoryIds(Set<Category> categories)
    {
        Collection<String> ids = new ArrayList<String>();
        for (Category category : categories)
            ids.add(category.id);

        return ids;
    }

}
