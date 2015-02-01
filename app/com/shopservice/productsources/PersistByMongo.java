package com.shopservice.productsources;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.shopservice.MailService;
import com.shopservice.MongoDataBase;
import com.shopservice.transfer.Product;
import play.Logger;
import play.libs.Json;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 09.08.2014.
 */
public class PersistByMongo extends ProductSourceWrapper {

    private DB dataBase = MongoDataBase.get();

    public PersistByMongo(ProductSource source) {
        super(source);
    }

    @Override
    public List<Product> get(Integer providerId) {
        dataBase.getCollection("products").drop();

        List<Product> products = super.get(providerId);
        dataBase.getCollection("products").insert(toList(products));

        return products;
    }

    private List<DBObject> toList(List<Product> products)
    {
        List<DBObject> list = new ArrayList<>();

        for (Product product : products) {
            list.add((DBObject)JSON.parse(Json.toJson(product).toString()));
        }

        return list;
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
