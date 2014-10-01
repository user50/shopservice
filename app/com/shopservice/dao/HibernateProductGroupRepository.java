package com.shopservice.dao;

import com.shopservice.HibernateUtil;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.shopservice.HibernateUtil.*;

/**
 * Created by user50 on 21.09.2014.
 */
public class HibernateProductGroupRepository implements ProductGroupRepository {
    @Override
    public List<ProductGroup> get(final String clientId) {
        return execute(new Query() {
            @Override
            public List<ProductGroup> execute(Session session) {
                ClientSettings settings = (ClientSettings) session.get(ClientSettings.class, clientId);

                return session.createCriteria(ProductGroup.class, "group")
                        .setFetchMode("group.clientSettings", FetchMode.JOIN)
                        .createAlias("group.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId )).list();
            }
        });
    }

    @Override
    public void save(final ProductGroup productGroup) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.save(productGroup);
            }
        });
    }

    @Override
    public String getName(final int groupId) {
        return execute(new Query() {
            @Override
            public String execute(Session session) {
                ProductGroup group = (ProductGroup)session.get(ProductGroup.class, groupId);
                return group.name;
            }
        });
    }

    @Override
    public boolean exist(final String clientId, final String name) {
        return execute(new Query() {
            @Override
            public Boolean execute(Session session) {
                return !session.createCriteria(ProductGroup.class, "group")
                        .setFetchMode("group.clientSettings", FetchMode.JOIN)
                        .createAlias("group.clientSettings", "settings", CriteriaSpecification.LEFT_JOIN)
                        .add(Restrictions.eq("settings.id", clientId ))
                        .add(Restrictions.eq("name", name ))
                        .list().isEmpty();
            }
        });
    }

    @Override
    public void remove(final int groupId) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                ProductGroup group = (ProductGroup)session.get(ProductGroup.class, groupId);
                session.delete(group);
            }
        });
    }

    @Override
    public ProductGroup get(final Long groupId) {
        return execute(new Query() {
            @Override
            public ProductGroup execute(Session session) {
                return  (ProductGroup)session.get(ProductGroup.class, groupId.intValue());
            }
        });
    }

    @Override
    public void update(final ProductGroup group) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.update(group);
            }
        });
    }
}
