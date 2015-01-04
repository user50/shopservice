package com.shopservice.sync;

import com.google.common.collect.Sets;
import com.shopservice.MServiceInjector;
import com.shopservice.MailService;
import com.shopservice.Services;
import com.shopservice.dao.MongoProductRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.dao.ProductRepository;
import com.shopservice.domain.ProductEntry;
import com.shopservice.productsources.Florange;
import com.shopservice.transfer.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Yevhen on 1/4/15.
 */
public class ArtemSyncProduct implements SyncProduct {
    private static ProductEntryRepository productEntryRepository = MServiceInjector.injector.getInstance(ProductEntryRepository.class);

    private final String clientId = "artem";
    private final Florange productRepository = new Florange();

    public ArtemSyncProduct() {
    }

    @Override
    public void execute() {
        List<Product> products = productRepository.get(null);
        Set<ProductEntry> productEntriesFromClient = new HashSet<>();
        for (Product product : products)
            productEntriesFromClient.add(new ProductEntry(product));

        Set<ProductEntry> productEntriesFromSettings = productEntryRepository.get(clientId);

        try {
            productEntryRepository.add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));
        } catch (Exception e) {
            MailService.getInstance().report(e);
        }
    }
}
