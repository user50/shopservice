package com.shopservice;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class Util {

    public static  <T> byte[] marshal(T t, String encoding) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        marshaller.marshal( t, outputStream );

        return outputStream.toByteArray();
    }

    public static int sum(Collection<Integer> values) {
        if(values==null || values.size()<1)
            return 0;

        int sum = 0;
        for(Integer i: values)
            sum = sum+i;

        return sum;
    }
}
