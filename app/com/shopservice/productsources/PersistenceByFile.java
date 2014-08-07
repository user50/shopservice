package com.shopservice.productsources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.shopservice.FileStorage;
import com.shopservice.MailService;
import com.shopservice.domain.Product;
import play.libs.Json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 06.08.2014.
 */
public class PersistenceByFile implements ProductSource {

    private ProductSource source;

    private FileStorage<List<Product>> storage;

    public PersistenceByFile(ProductSource source, String fileName) {
        this.source = source;
        storage = new FileStorage<List<Product>>(fileName) {
            @Override
            protected List<Product> construct(JsonNode node) {
                ArrayNode array = (ArrayNode)node;

                List<Product> persistedProducts = new ArrayList<>();
                for (int i=0;i<array.size(); i++)
                    persistedProducts.add(Json.fromJson(array.get(i),Product.class ));

                return persistedProducts;
            }
        };
    }

    @Override
    public List<Product> get(Integer providerId) {
        try {
            if (storage.isExist())
                return storage.get();

            List<Product> products = source.get(providerId);
            storage.save(products);

            return products;
        } catch (IOException e) {
            MailService.getInstance().report(e);

            return source.get(providerId);
        }
    }
}
