package com.shopservice.dao;

import com.google.common.collect.Sets;
import com.shopservice.MServiceInjector;
import com.shopservice.MailService;
import com.shopservice.ProductConditions;
import com.shopservice.transfer.Product;
import com.shopservice.domain.ProductEntry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user50 on 21.06.2014.
 */
public class SynchronizeProducts extends ProductRepositoryWrapper {

    ProductEntryRepository productEntryRepository = MServiceInjector.injector.getInstance(ProductEntryRepository.class);
    ProductRepository productRepository;

    private String clientId;

    public SynchronizeProducts(ProductRepository productRepository, String clientId) {
        super(productRepository);
        this.clientId = clientId;

        if (clientId.equals("artem"))
            this.productRepository = productRepository;
        else
            this.productRepository = new JdbcProductRepository(clientId);
    }

    @Override
    public List<Product> find(ProductConditions query) {
        syncProducts();
        return super.find(query);
    }

    @Override
    public List<Product> find() {
        syncProducts();
        return super.find();
    }

    private void syncProducts() {
        Runnable runnable = new Runnable() {
            public void run() {
                List<Product> products = productRepository.find();
                Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
                for (Product product : products)
                    productEntriesFromClient.add(new ProductEntry(product));

                Set<ProductEntry> productEntriesFromSettings = productEntryRepository.get(clientId);

                try {
                    productEntryRepository.delete(Sets.difference(productEntriesFromSettings, productEntriesFromClient));
                    productEntryRepository.add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));
                } catch (Exception e) {
                    MailService.getInstance().report(e);
                }

            }
        };

        new Thread(runnable).start();
    }
}
