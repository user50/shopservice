package com.shopservice.assemblers;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.shopservice.Services;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;

import java.util.*;

public class ProductAssembler {

    ProductEntryRepository productEntryRepository;

    @Inject
    public ProductAssembler(ProductEntryRepository productEntryRepository) {
        this.productEntryRepository = productEntryRepository;
    }

    public Collection<ProductEntry> getProducts(String clientId, String categoryId, int groupId) throws Exception {
        List<Product> products = syncProducts(clientId, categoryId);

        Map<String,ProductEntry> productEntries = new HashMap<String, ProductEntry>();
        for (ProductEntry entry : productEntryRepository.getWithChecked(clientId, categoryId, groupId) )
            productEntries.put(entry.productId, entry );

        for (Product product : products)
            fill(productEntries.get(product.id), product);

       return productEntries.values();
    }

    private void fill(ProductEntry productEntry, Product product) {
        productEntry.url = product.url;
        productEntry.price = product.price;
        productEntry.productName = product.name;
        productEntry.published = product.published;
        productEntry.categoryName = product.category.name;

    }

    public PaginationResult<ProductEntry> getProductsPage(String clientId, String categoryId, int groupId, int offset, int limit) throws Exception {
        List<Product> products = syncProducts(clientId, categoryId);

        Map<String,ProductEntry> productEntriesPage = new LinkedHashMap<String,ProductEntry>();
        for (ProductEntry entry : productEntryRepository.getWithCheckedPage(clientId, categoryId, groupId, offset, limit) )
            productEntriesPage.put(entry.productId, entry );

        for (Product product : products) {
            if (productEntriesPage.keySet().contains(product.id))
                fill(productEntriesPage.get(product.id), product);
        }

        return new PaginationResult<ProductEntry>(products.size(), productEntriesPage.values());
    }

    private List<Product> syncProducts(String clientId, String categoryId) throws Exception {
        List<Product> products = Services.getProductDAO(clientId).getProducts(categoryId);
        Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
        for (Product product : products)
            productEntriesFromClient.add(new ProductEntry(product));

        Set<ProductEntry> productEntriesFromSettings = productEntryRepository.get(clientId, categoryId);

        productEntryRepository.delete(Sets.difference(productEntriesFromSettings, productEntriesFromClient));

        productEntryRepository.add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));

        return products;
    }

    public List<ProductEntry> getProductsByWords(String clientId, int groupId, String words) throws Exception {
        List<Product> products = Services.getProductDAO(clientId).findProductsByWords(Arrays.asList(words.trim().split(" ")));

        Map<String,ProductEntry> result = new LinkedHashMap<String,ProductEntry>();
        for (ProductEntry entry : productEntryRepository.findSelected(clientId, groupId) )
            result.put(entry.productId, entry );

        for (Product product : products) {
            if (result.keySet().contains(product.id))
                fill(result.get(product.id), product);
        }

        return null;
    }
}
