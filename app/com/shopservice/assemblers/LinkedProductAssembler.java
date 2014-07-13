package com.shopservice.assemblers;

import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.LinkedProductEntryRepository;
import com.shopservice.domain.LinkedProductEntry;
import com.shopservice.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user50 on 13.07.2014.
 */
public class LinkedProductAssembler {
    private LinkedProductEntryRepository repository = MServiceInjector.injector.getInstance(LinkedProductEntryRepository.class);

    private static LinkedProductAssembler instance;

    public static LinkedProductAssembler getInstance() {
        if(instance == null)
            instance = new LinkedProductAssembler();

        return instance;
    }

    public List<LinkedProductEntry> find(String clientId, Integer providerId)
    {
        List<LinkedProductEntry> entries = repository.find(providerId);

        List<String> productIds = new ArrayList<>();
        for (LinkedProductEntry entry : entries)
            productIds.add(entry.productEntry.productId);

        List<Product> products = Services.getProductDAO(clientId).find(new ProductConditions(productIds));

        Map<String,Product> productMap = new HashMap<>();
        for (Product product : products)
            productMap.put(product.id, product);

        for (LinkedProductEntry entry : entries) {
            entry.productEntryId = entry.productEntry.id;
            entry.clientProductsName = productMap.get(entry.productEntry.productId).name;
        }

        return entries;
    }
}
