package com.shopservice.urlgenerate;

import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.transfer.Product;

import static com.shopservice.MServiceInjector.injector;

/**
 * Created by user50 on 14.06.2015.
 */
public class Bilasad extends UrlGenerator {

    @Override
    public String getClientId() {
        return "client3";
    }

    @Override
    public String generateProductUrl(Product product) {
        return "http://bilasad.com/" + product.url;
    }

    @Override
    public String generateProductImageUrl(Product product) {
        return "http://bilasad.com/media/catalog/product" + product.imageUrl;
    }
}
