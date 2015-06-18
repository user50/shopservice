package com.shopservice;

import com.shopservice.transfer.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yevhen on 2015-06-17.
 */
public class FilterNotAvailable implements CollectionFilter<Product> {

    @Override
    public List<Product> filter(Collection<Product> toFilter) {
        Iterator<Product> iterator = toFilter.iterator();
        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!product.available)
                iterator.remove();
        }
        return new ArrayList<>(toFilter);
    }
}
