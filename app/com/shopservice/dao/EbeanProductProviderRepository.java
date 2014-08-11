package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductProvider;

import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public class EbeanProductProviderRepository implements ProductProviderRepository {
    @Override
    public List<ProductProvider> find(String clientId) {
        return Ebean.find(ProductProvider.class).where().eq("client_settings_id", clientId).findList();
    }

    @Override
    public ProductProvider find(Integer providerId) {
        return Ebean.find(ProductProvider.class, providerId);
    }

    @Override
    public ProductProvider create(String clientId, ProductProvider productProvider) {
        ClientSettings clientSettings = Ebean.find(ClientSettings.class, clientId);
        clientSettings.productProviders.add(productProvider);

        Ebean.save(clientSettings);

        return productProvider;
    }

    @Override
    public ProductProvider update(ProductProvider productProvider) {
        ProductProvider persistedProvider = Ebean.find(ProductProvider.class, productProvider.id);

        persistedProvider.name = productProvider.name;
        persistedProvider.url = productProvider.url;
        persistedProvider.margin = productProvider.margin;

        Ebean.save(persistedProvider);

        return persistedProvider;
    }

    @Override
    public void remove(Integer providerId) {
        Ebean.delete(ProductProvider.class, providerId);
    }

}
