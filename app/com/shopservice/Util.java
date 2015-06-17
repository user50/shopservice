package com.shopservice;


import com.shopservice.transfer.Product;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String DTD_DECLARATION = "<!DOCTYPE yml_catalog SYSTEM \"shops.dtd\">\n";

    public static  <T> byte[] marshal(T t, String encoding) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", DTD_DECLARATION);

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

    public static String reverseFistChar(String text)
    {
        char[] chars = text.toCharArray();
        char c = chars[0];

        if (Character.isUpperCase(c))
            chars[0] = Character.toLowerCase(c);
        else if (Character.isLowerCase(c))
            chars[0] = Character.toUpperCase(c);

        return new String(chars);
    }

    public static String stringify(Collection<String> words)
    {
        StringBuilder buffer = new StringBuilder();
        for (String word : words) {
            buffer.append(word).append(" ");
            buffer.append(Util.reverseFistChar(word)).append(" ");
        }

        return buffer.toString();
    }

    public static void removeNotAvailable(Set<Product> products) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!product.available)
                iterator.remove();
        }
    }
}
