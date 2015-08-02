package com.shopservice;

import com.shopservice.transfer.Product;

import java.util.Collection;

/**
 * Created by Yevhen on 2015-06-17.
 */
public interface CollectionFilter<T> {
    Collection<Product> filter(Collection<T> toFilter);
}
