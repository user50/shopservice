package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.shopservice.HibernateUtil.*;

/**
 * Created by user50 on 20.09.2014.
 */
public class HibernateClientSettingsRepository implements ClientSettingsRepository {
    @Override
    public ClientSettings get(final String id) {
        return execute(new Query<ClientSettings>() {
            @Override
            public ClientSettings execute(Session session) {
                return (ClientSettings) session.get(ClientSettings.class, id);
            }
        });
    }

    @Override
    public List<ClientSettings> get() {
        return execute(new Query() {
            @Override
            public List<ClientSettings> execute(Session session) {
                return session.createCriteria(ClientSettings.class).list();
            }
        });
    }

    @Override
    public void remove(final String id) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ClientSettings settings = (ClientSettings) session.get(ClientSettings.class, id);
                session.delete(settings);
            }
        });
    }

    @Override
    public void save(final ClientSettings clientSettings) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.save(clientSettings);
            }
        });
    }

    @Override
    public void update(final ClientSettings clientSettings) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.update(clientSettings);
            }
        });
    }

    @Override
    public ClientSettings getBySiteName(final String siteName) {
        return execute(new Query() {
            @Override
            public ClientSettings execute(Session session) {
                return (ClientSettings) session.createCriteria( ClientSettings.class ).
                        add( Restrictions.eq("siteName", siteName) ).
                        uniqueResult();
            }
        });
    }
}
