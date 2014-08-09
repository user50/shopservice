package com.shopservice.productsources;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.shopservice.MailService;
import com.shopservice.domain.Product;
import play.Logger;
import play.libs.Json;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 09.08.2014.
 */
public class PersistByMongo extends ProductSourceWrapper {

    private DB dataBase;

    public PersistByMongo(ProductSource source) {
        super(source);

        MongoClient mongoClient = createMongoClient();
        dataBase = mongoClient.getDB("heroku_app23077977");
    }

    private MongoClient createMongoClient()
    {
        try {
            String url = System.getenv("MONGOLAB_URI");

            if (url != null)
                return new MongoClient( new MongoClientURI(url) );
            else
                return new MongoClient( new MongoClientURI("mongodb://heroku_app23077977:s75c3rqqo1c6h7l838v71osr5@dbh73.mongolab.com:27737/heroku_app23077977"));
        } catch (UnknownHostException e) {
            Logger.error( "Error during creating mongo client", e);
            MailService.getInstance().report(e);

            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> get(Integer providerId) {
        if (!dataBase.collectionExists("products"))
        {
            List<Product> products = super.get(providerId);
            dataBase.getCollection("products").insert(toList(products));
            return products;
        }

        List<DBObject> objects = dataBase.getCollection("products").find().toArray();

        return toProductList(objects);
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
