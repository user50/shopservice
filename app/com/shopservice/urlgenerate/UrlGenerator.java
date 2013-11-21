package com.shopservice.urlgenerate;

import com.shopservice.domain.Product;

public abstract class UrlGenerator {

    public abstract String getClientId();

    public abstract String generateProductUrl(Product product);

    public abstract String generateProductImageUrl(Product product);
}
