package com.shopservice.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.shopservice.MongoDataBase;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;
import play.libs.Json;

import java.util.*;

/**
 * Created by user50 on 21.12.2014.
 */
public class DemoCategoryRepository implements CategoryRepository {

    public static final String DEMO_CATEGORIES_COLLECTION = "demoCategory";

    private DB dataBase = MongoDataBase.get();

    @Override
    public List<Category> getCategories() throws Exception {
        List<DBObject> categories = dataBase.getCollection(DEMO_CATEGORIES_COLLECTION).find().toArray();

        return toList(categories);
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        List<DBObject> categories = dataBase.getCollection(DEMO_CATEGORIES_COLLECTION)
                .find(new BasicDBObject("parentId", new BasicDBObject("$in", categoryIds))).toArray();


        return new HashSet<>(toList(categories));
    }

    private List<Category> toList(List<DBObject> objects)
    {
        List<Category> list = new ArrayList<>();

        for (DBObject object : objects) {
            list.add(Json.fromJson(Json.parse(object.toString()), Category.class));
        }

        return list;
    }
}
