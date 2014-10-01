package com.shopservice.dao;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.shopservice.domain.ClientSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedClientSettingsRepository implements ClientSettingsRepository {

    public static final String BASE = "CachedClientSettingsRepository.base";

    ClientSettingsRepository wrapped;

    private Map<String,ClientSettings> clientSettings = new HashMap<String, ClientSettings>();

    @Inject
    public CachedClientSettingsRepository(@Named(BASE) ClientSettingsRepository wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ClientSettings get(String id) {
        if (!clientSettings.containsKey(id))
            clientSettings.put(id, wrapped.get(id));

        return clientSettings.get(id);
    }

    @Override
    public List<ClientSettings> get() {
        return wrapped.get();
    }

    @Override
    public void remove(String id) {
        wrapped.remove(id);
        clientSettings.remove(id);
    }

    @Override
    public void save(ClientSettings clientSettings) {
        wrapped.save(clientSettings);

        if (clientSettings.id != null)
            this.clientSettings.remove(clientSettings.id);
    }

    @Override
    public void update(ClientSettings clientSettings) {
        wrapped.update(clientSettings);

        if (clientSettings.id != null)
            this.clientSettings.remove(clientSettings.id);
    }

    @Override
    public ClientSettings getBySiteName(String siteName) {
        return wrapped.getBySiteName(siteName);
    }
}
