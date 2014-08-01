package com.shopservice.assemblers;

import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.LinkedProductEntryRepository;
import com.shopservice.domain.LinkedProductEntry;
import com.shopservice.transfer.Product;

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

    public List<LinkedProductEntry> find(String clientId, Integer providerId, Boolean linked)
    {
        List<LinkedProductEntry> entries = filterLinkedEntries(repository.find(providerId), linked);

        if (!linked)
            return entries;

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

    private List<LinkedProductEntry> filterLinkedEntries(List<LinkedProductEntry> entries, boolean linked) {
        List<LinkedProductEntry> filteredResult = new ArrayList<>();

        for (LinkedProductEntry entry : entries)
            if (entry.productEntry != null ^ !linked)
                filteredResult.add(entry);

        return filteredResult;
    }
}
