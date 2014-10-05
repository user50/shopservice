package com.shopservice.assemblers;

import com.google.inject.Inject;
import com.shopservice.CollectionTransformer;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.transfer.Product;
import com.shopservice.domain.ProductEntry;

import java.util.*;

public class ProductAssembler {

    ProductEntryRepository productEntryRepository;

    @Inject
    public ProductAssembler(ProductEntryRepository productEntryRepository) {
        this.productEntryRepository = productEntryRepository;
    }

    public PaginationResult<ProductEntry> getProductsPage(String clientId, int groupId, ProductConditions conditions ) throws Exception {
        List<Product> products = Services.getProductDAO(clientId).find(conditions);

        List<String> productIds = new CollectionTransformer().transform(products, new CollectionTransformer.Transformer<String, Product>()
        {
            @Override
            public String transform(Product product) {
                return product.id;
            }
        });

        Map<String,ProductEntry> productEntriesPage = new LinkedHashMap<String,ProductEntry>();
        for (ProductEntry entry : productEntryRepository.get(groupId, productIds) )
            productEntriesPage.put(entry.productId, entry );

        for (Product product : products) {
            if (productEntriesPage.keySet().contains(product.id))
                fill(productEntriesPage.get(product.id), product);
        }

        return new PaginationResult<ProductEntry>(Services.getProductDAO(clientId).size(conditions), productEntriesPage.values());
    }

    private void fill(ProductEntry productEntry, Product product) {
        productEntry.url = product.url;
        productEntry.price = product.price;
        productEntry.productName = product.name;
        productEntry.published = product.published;
        productEntry.categoryName = product.category.name;
        if (productEntry.description == null)
            productEntry.description = product.description;

    }
}
