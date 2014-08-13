package com.shopservice;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.shopservice.productsources.ProductSource;
import play.Logger;

import java.net.UnknownHostException;

/**
 * Created by user50 on 09.08.2014.
 */
public class MongoDataBase {
    private static DB dataBase;

    public static DB get()
    {
        if (dataBase != null)
            return dataBase;

        try {
            String url = System.getenv("MONGOLAB_URI");

            if (url != null)
                dataBase = new MongoClient( new MongoClientURI(url) ).getDB("heroku_app23077977");
            else
                return new MongoClient().getDB("test");
        } catch (UnknownHostException e) {
            Logger.error("Error during creating mongo client", e);
            MailService.getInstance().report(e);

            throw new RuntimeException(e);
        }

        return dataBase;
    }

}
