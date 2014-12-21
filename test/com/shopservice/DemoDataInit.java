package com.shopservice;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.shopservice.dao.*;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;
import org.junit.Test;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 21.12.2014.
 */
public class DemoDataInit {

    private DB dataBase = MongoDataBase.get();

    @Test
    public void initCategory() throws Exception {
        CategoryRepository categoryRepository = new JdbcCategoryRepository("client1");
        List<Category> categories = categoryRepository.getCategories();

        List<DBObject> list = new ArrayList<>();

        for (Category category : categories) {
            list.add((DBObject) JSON.parse(Json.toJson(category).toString()));
        }

        dataBase.getCollection(DemoCategoryRepository.DEMO_CATEGORIES_COLLECTION).insert(list);
    }

    @Test
    public void initProducts() throws Exception {
        ProductRepository productRepository = new JdbcProductRepository("client1");
        List<Product> products = productRepository.find();

        List<DBObject> list = new ArrayList<>();

        for (Product product : products) {
            list.add((DBObject) JSON.parse(Json.toJson(product).toString()));
        }

        dataBase.getCollection("demoProducts").insert(list);


    }
}
