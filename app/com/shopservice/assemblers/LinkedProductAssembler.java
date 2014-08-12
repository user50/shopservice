package com.shopservice.assemblers;

import com.shopservice.LinkedEntryCondition;
import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.LinkedProductEntryRepository;
import com.shopservice.domain.LinkedProductEntry;
import com.shopservice.transfer.Product;

import java.util.*;

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

    public List<LinkedProductEntry> find(String clientId, Integer providerId, Boolean linked, String contains, Integer limit, Integer offset)
    {

        if (contains != null)
        {
            List<String> productIds = new ArrayList<>();

            ProductConditions conditions = new ProductConditions();
            conditions.words = Arrays.asList(contains.split(" "));

            List<Product> products = Services.getProductDAO(clientId).find(conditions);

            for (Product product : products) {
                productIds.add(product.id);
            }

            List<LinkedProductEntry> entries = repository.find(new LinkedEntryCondition(providerId, linked, productIds, limit, offset));
            fillLinkedEntries(entries, products);

            return entries;
        }

        List<LinkedProductEntry> entries = repository.find(new LinkedEntryCondition(providerId, linked, limit, offset));

        if (!linked){
            return entries;
        }
        List<String> productIds = new ArrayList<>();
        for (LinkedProductEntry entry : entries)
            productIds.add(entry.productEntry.productId);

        List<Product> products = Services.getProductDAO(clientId).find(new ProductConditions(productIds));

        fillLinkedEntries(entries, products);
        return entries;
    }

    private void fillLinkedEntries(List<LinkedProductEntry> entries, List<Product> products)
    {
        Map<String,Product> productMap = new HashMap<>();
        for (Product product : products)
            productMap.put(product.id, product);

        for (LinkedProductEntry entry : entries) {
            entry.productEntryId = entry.productEntry.id;
            entry.clientProductsName = productMap.get(entry.productEntry.productId).name;
        }
    }

    private List<LinkedProductEntry> filterLinkedEntries(List<LinkedProductEntry> entries, boolean linked) {
        List<LinkedProductEntry> filteredResult = new ArrayList<>();

        for (LinkedProductEntry entry : entries)
            if (entry.productEntry != null ^ !linked)
                filteredResult.add(entry);

        return filteredResult;
    }
}
