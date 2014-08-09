package com.shopservice.dao;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.shopservice.MongoDataBase;
import com.shopservice.ProductConditions;
import com.shopservice.Util;
import com.shopservice.domain.Product;
import play.libs.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user50 on 09.08.2014.
 */
public class FlorangeProductRepositoryByMongo implements ProductRepository {

    private final static String COLLECTION_NAME = "products";
    private DB dataBase = MongoDataBase.get();

    @Override
    public List<Product> find(ProductConditions conditions) {

        DBCursor cursor = dataBase.getCollection(COLLECTION_NAME).find(makeMongoQuery(conditions));

        if (conditions.limit != null)
            cursor = cursor.limit(conditions.limit);

        if (conditions.offset != null)
            cursor = cursor.skip(conditions.offset);

        return toProductList(cursor.toArray());
    }

    @Override
    public List<Product> find() {
        return find(new ProductConditions());
    }

    @Override
    public int size(ProductConditions conditions) {
        return dataBase.getCollection(COLLECTION_NAME).find(makeMongoQuery(conditions)).count();
    }

    private BasicDBObject makeMongoQuery(ProductConditions conditions)
    {
        BasicDBObject query = new BasicDBObject();

        if (!conditions.productIds.isEmpty()) {
            BasicDBList list = new BasicDBList();
            list.addAll(conditions.productIds);
            query.append("id", new BasicDBObject("$in", list));
        }

        if (conditions.categoryId != null)
            query.append("category.id", conditions.categoryId );

        if (!conditions.words.isEmpty())
        {
            BasicDBList list = new BasicDBList();

            for (String word : conditions.words)
                list.add(new BasicDBObject("name", new BasicDBObject("$regex", ".*"+word+".*").append("$options", "-i")) );

            query.append("$or", list);
        }

        return query;
    }

    private List<Product> toProductList(List<DBObject> objects)
    {
        List<Product> list = new ArrayList<>();

        for (DBObject object : objects) {
            list.add(Json.fromJson(Json.parse(object.toString()), Product.class));
        }

        return list;
    }
}
