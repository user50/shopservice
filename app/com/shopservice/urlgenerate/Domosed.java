package com.shopservice.urlgenerate;

import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.transfer.Product;

import static com.shopservice.MServiceInjector.injector;

public class Domosed extends UrlGenerator {

    private ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    @Override
    public String getClientId() {
        return "client1";
    }

    @Override
    public String generateProductUrl(Product product) {
        return clientSettingsRepository.get(getClientId()).pathToProductPage + product.url;

    }

    @Override
    public String generateProductImageUrl(Product product) {
        return clientSettingsRepository.get(getClientId()).pathToProductImage + product.imageUrl;
    }
}
