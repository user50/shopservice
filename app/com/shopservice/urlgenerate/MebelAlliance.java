package com.shopservice.urlgenerate;

import com.shopservice.domain.Product;

public class MebelAlliance extends UrlGenerator {
    @Override
    public String getClientId() {
        return "client2";
    }

    @Override
    public String generateProductUrl(Product product) {
        return null;
    }

    @Override
    public String generateProductImageUrl(Product product) {
        return null;
    }
}
