package com.shopservice.dao;

import com.shopservice.domain.ClientSettings;

import java.util.List;

public interface ClientSettingsRepository {

    ClientSettings get(String id);

    List<ClientSettings> get();

    void remove(String id);

    void save(ClientSettings clientSettings);

    void update(ClientSettings clientSettings);

    ClientSettings getBySiteName(String siteName);
}
