package com.shopservice;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Product;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

public class Util {

    public static  <T> void save (T t, String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");

        FileOutputStream outputStream = new FileOutputStream(filePath);

        marshaller.marshal( t, outputStream );
    }

    public static void modifyUrl(String clientId, Product product) throws SQLException {
        if (product.url == null){
            //todo get url;
        } else {
            product.setUrl(ClientSettings.findById(clientId).pathToProductPage + product.url);
        }
    }

    public static void modifyImageUrl(String clientId, Product product) throws SQLException {
        if (product.url == null){
            //todo get url;
        } else {
            product.setImageUrl(ClientSettings.findById(clientId).pathToProductImage + product.imageUrl);
        }
    }
}
