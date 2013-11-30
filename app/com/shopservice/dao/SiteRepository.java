package com.shopservice.dao;

import com.shopservice.domain.Site;

import java.util.List;

public interface SiteRepository {
    List<Site> get(String clientId);

    void save(Site site);

    String getName(int id);

    boolean exist(String clientId, String name);

    void remove(int id);
}
