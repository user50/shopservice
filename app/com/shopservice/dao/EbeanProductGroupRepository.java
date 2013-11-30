package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ProductGroup;

import java.util.List;

public class EbeanProductGroupRepository implements ProductGroupRepository {
    @Override
    public List<ProductGroup> get(String clientId) {
        return Ebean.find(ProductGroup.class).where().eq("client_settings_id", clientId).findList();
    }

    @Override
    public void save(ProductGroup productGroup) {
        Ebean.save(productGroup);
    }

    @Override
    public String getName(int id) {
        return Ebean.find(ProductGroup.class, id).name;
    }

    @Override
    public boolean exist(String clientId, String name) {
        return Ebean.find(ProductGroup.class).where().eq("client_settings_id", clientId).eq("name", name).findUnique() != null;
    }

    @Override
    public void remove(int id) {
        Ebean.delete(ProductGroup.class, id);
    }
}
