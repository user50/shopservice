package com.shopservice;

import com.shopservice.domain.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import scala.util.parsing.combinator.testing.Str;

/**
 * Created by user50 on 05.08.2014.
 */
public class FlorangeTest {

    @Test
    public void testName() throws Exception {
        Document doc = Jsoup.connect("http://florange.ua/ru/production/catalog/basic2014/dana/").get();

        String generalName = doc.select(".name").first().text();

        Elements tabs = doc.select(".tabs");

        Elements prices = null;
        if (doc.select(".oldprice").isEmpty())
            prices = doc.select(".newprice1");
        else
            prices = doc.select(".newprice");

        int i = 0;
        for (Element tabLi : tabs.get(0).children()){
            Product product = new Product();

            String id = tabLi.id();
            Element span = tabLi.getElementsByTag("span").get(0);
            Attributes attr = span.child(0).attributes();
            String productName = attr.get("title") + " " + generalName;
            String productPrice = prices.get(i).text().replace("грн.", "").trim();
            String imageUrl = doc.select("#slide").get(0).children().get(i).children().first().attributes().get("src");

            product.name = productName;
            product.price = Double.valueOf(productPrice);
            product.imageUrl = imageUrl;

            i++;
        }

    }

    @Test
    public void testName1() throws Exception {
        Document doc = Jsoup.connect("http://florange.ua/ru/production/catalog/").get();

        Elements categories = doc.select(".nav").first().children();

        for (Element category : categories){
            String url = category.child(0).attributes().get("href");
            System.out.println(url);
        }



    }
}
