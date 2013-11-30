package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;

import java.util.List;

public class EbeanClientSettingsRepository implements ClientSettingsRepository {
    @Override
    public ClientSettings findById(String id) {
        return Ebean.find(ClientSettings.class, id);
    }

    @Override
    public List<ClientSettings> getAll() {
        return Ebean.find(ClientSettings.class).findList();
    }

    @Override
    public void remove(String id) {
        Ebean.delete(ClientSettings.class, id);
    }

    @Override
    public void save(ClientSettings clientSettings) {
        Ebean.save(clientSettings);
    }

    @Override
    public void update(ClientSettings clientSettings) {
        Ebean.update(clientSettings);
    }

    @Override
    public ClientSettings getBySiteName(String siteName) {
        return Ebean.find(ClientSettings.class).where().eq("siteName", siteName).findUnique();
    }
}
