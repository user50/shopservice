package com.shopservice;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.cache.Cache;
import play.mvc.Http;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getCurrentClientId()
    {
        String cookie = Http.Context.current().request().cookie("key").value();

        return (String) Cache.get(cookie);
    }

    public static boolean matches(String regex, String sentence)
    {
        Pattern pattern = Pattern.compile(regex,
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(sentence);

        return matcher.find();
    }

    public static Document connect(String url) throws IOException {
        return Jsoup.connect(url).timeout(10 * 1000).get();
    }

}
