package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ClientsCategory;

import java.util.List;

public class EbeanClientsCategoryRepository implements ClientsCategoryRepository{
    @Override
    public List<ClientsCategory> get(String clientId) {
        return Ebean.find(ClientsCategory.class).where().eq("client_settings_id", clientId).findList();
    }

    @Override
    public ClientsCategory add(String clientId, ClientsCategory clientsCategory) {
        ClientSettings clientSettings = Ebean.find(ClientSettings.class, clientId);
        clientSettings.clientsCategories.add(clientsCategory);
        Ebean.save(clientSettings);

        return clientsCategory;
    }

    @Override
    public ClientsCategory update(String clientId, ClientsCategory clientsCategory) {
        Ebean.update(clientsCategory);

        return clientsCategory;
    }

    @Override
    public void delete(int clientsCategoryId) {
        Ebean.createSqlUpdate("DELETE clients_category.* FROM clients_category WHERE id = ?").setParameter(1, clientsCategoryId).execute();
    }
}
