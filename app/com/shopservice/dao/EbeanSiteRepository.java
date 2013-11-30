package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.Site;

import java.util.List;

public class EbeanSiteRepository implements SiteRepository {
    @Override
    public List<Site> get(String clientId) {
        return Ebean.find(Site.class).where().eq("client_settings_id", clientId).findList();
    }

    @Override
    public void save(Site site) {
        Ebean.save(site);
    }

    @Override
    public String getName(int id) {
        return Ebean.find(Site.class, id).name;
    }

    @Override
    public boolean exist(String clientId, String name) {
        return Ebean.find(Site.class).where().eq("client_settings_id", clientId).eq("name", name).findUnique() != null;
    }

    @Override
    public void remove(int id) {
        Ebean.delete(Site.class, id);
    }
}
