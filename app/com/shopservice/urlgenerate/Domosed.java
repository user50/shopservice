package com.shopservice.urlgenerate;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Product;

public class Domosed extends UrlGenerator {

    @Override
    public String getClientId() {
        return "client1";
    }

    @Override
    public String generateProductUrl(Product product) {
        return ClientSettings.findById(getClientId()).pathToProductPage + product.url;

    }

    @Override
    public String generateProductImageUrl(Product product) {
        return ClientSettings.findById(getClientId()).pathToProductImage + product.imageUrl;
    }
}
