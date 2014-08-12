package com.shopservice.productsources;


import com.shopservice.transfer.Product;

import java.util.List;

/**
 * Created by user50 on 09.08.2014.
 */
public class ProductSourceWrapper implements ProductSource {

    private ProductSource source;

    public ProductSourceWrapper(ProductSource source) {
        this.source = source;
    }

    @Override
    public List<Product> get(Integer providerId) {
        return source.get(providerId);
    }
}
