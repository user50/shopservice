package com.shopservice;

import com.shopservice.dao.MongoProductRepository;
import com.shopservice.sync.ArtemSyncProduct;
import com.shopservice.sync.DefaultSyncProducts;
import com.shopservice.domain.ClientSettings;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by Yevhen on 1/4/15.
 */
public class SyncTest {

    @Test
    public void testSync() throws Exception {
        new ArtemSyncProduct().execute();
    }

    @Test
    public void testPerformance() throws Exception {
        while (true){
            HibernateUtil.execute(new HibernateUtil.Query() {
                @Override
                public String execute(Session session) {
                    session.get(ClientSettings.class, "demo");
                    return null;
                }
            });
        }

    }
}
