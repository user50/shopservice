package com.shopservice.urlgenerate;

import com.shopservice.domain.Product;

public class DefaultGenerator extends UrlGenerator {

    private final static DefaultGenerator instance = new DefaultGenerator();

    private DefaultGenerator() {
    }

    public static DefaultGenerator getInstance() {
        return instance;
    }

    @Override
    public String getClientId() {
        return null;
    }

    @Override
    public String generateProductUrl(Product product) {
        return product.url;
    }

    @Override
    public String generateProductImageUrl(Product product) {
        return product.imageUrl;
    }
}
