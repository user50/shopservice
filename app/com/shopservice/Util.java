package com.shopservice;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Util {

    public static  <T> void save (T t, String filePath, String encoding) throws JAXBException, FileNotFoundException {
        new File(filePath).getParentFile().mkdirs();

        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        FileOutputStream outputStream = new FileOutputStream(filePath);

        marshaller.marshal( t, outputStream );
    }

}
