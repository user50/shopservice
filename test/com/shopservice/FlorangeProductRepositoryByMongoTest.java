package com.shopservice;

import com.shopservice.dao.FlorangeProductRepositoryByMongo;
import org.junit.Test;

/**
 * Created by user50 on 09.08.2014.
 */
public class FlorangeProductRepositoryByMongoTest {

    @Test
    public void testName() throws Exception {

        FlorangeProductRepositoryByMongo repo = new FlorangeProductRepositoryByMongo();

        ProductConditions conditions = new ProductConditions();
//        conditions.productIds.add("1784303933");
//        conditions.productIds.add("294166772");
//        conditions.productIds.add("1511267979");

//        conditions.categoryId = "994546208";

        conditions.words.add("трусики");

        repo.find(conditions);

    }
}
