package com.shopservice;

import com.shopservice.transfer.Product;

import java.util.*;

/**
 * Created by Yevhen on 2015-06-17.
 */
public class RemoveDuplicatesFilter implements CollectionFilter<Product> {
    @Override
    public List<Product> filter(Collection<Product> toFilter) {
        Map<String, Product> productMap = new HashMap<>();
        for (Product product : toFilter) {
            productMap.put(product.id, product);
        }

        return new ArrayList<>(productMap.values());
    }
}
