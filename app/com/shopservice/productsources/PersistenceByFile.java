package com.shopservice.productsources;

import com.fasterxml.jackson.databind.node.ArrayNode;
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
    private String fileName;

    public PersistenceByFile(ProductSource source, String fileName) {
        this.source = source;
        this.fileName = fileName;
    }

    @Override
    public List<Product> get(Integer providerId) {
        File file = new File(fileName);
        if (!file.exists())
        {
            List<Product> products = source.get(providerId);

            try {

                String textToPersist = Json.toJson(products).toString();
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(textToPersist);
                fileWriter.close();

                return products;

            } catch (IOException e) {
                MailService.getInstance().report(e);
                return products;
            }
        }

        try {
            String persistedText = new String(Files.readAllBytes(Paths.get( fileName )));
            ArrayNode array = (ArrayNode)Json.parse(persistedText);

             List<Product> persistedProducts = new ArrayList<>();
            for (int i=0;i<array.size(); i++)
                persistedProducts.add(Json.fromJson(array.get(i),Product.class ));

            return persistedProducts;

        } catch (IOException e) {
            MailService.getInstance().report(e);

            return source.get(providerId);
        }
    }
}
