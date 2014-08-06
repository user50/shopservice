package com.shopservice.productsources;

import com.shopservice.Util;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import play.Logger;
import play.api.libs.Crypto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 06.08.2014.
 */
public class Florange implements ProductSource {
    public static final String SITE_URL = "http://florange.ua";

    @Override
    public List<Product> get(Integer providerId) {
        List<Product> products = new ArrayList<>();

        try {
            for (String productUrl : productUrls()){
                products.addAll(getProducts(productUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return products;
    }

    private List<Product> getProducts(String url) throws IOException {
        System.out.println("Parsing of the " + url);
        List<Product> products = new ArrayList<>();

        try{
            Document doc = Util.connect(url);

            String generalName = doc.select(".name").first().text();

            Elements tabs = doc.select(".tabs > li");

            Elements prices = null;
            if (doc.select(".oldprice").isEmpty())
                prices = doc.select(".newprice1");
            else
                prices = doc.select(".newprice");

            int i = 0;
            for (Element tabLi : tabs){
                Product product = new Product();

                Element tabImg = tabLi.getElementsByTag("img").get(0);
                Attributes attr = tabImg.attributes();
                String productName = attr.get("title") + " " + generalName;

                String productPrice = prices.get(i).text().replace("грн.", "");
                if (productPrice.matches( ". шт -.*" ))
                    productPrice = productPrice.split("-")[1].trim();

                Elements images = doc.select("ul#slide > li > img");
                String imageUrl = null;
                if (!images.isEmpty())
                    imageUrl = images.get(i).attributes().get("src");

                Element cat = doc.select(".nav > li > ul").get(0);
                String categoryName = cat.parent().select("a").first().attributes().get("title");

                Category category = new Category();
                category.id = String.valueOf(categoryName.hashCode());
                category.name = categoryName;

                product.id = String.valueOf(productName.hashCode());
                product.name = productName;
                product.price = Double.valueOf(productPrice.replace(",", "."));
                product.imageUrl = imageUrl;
                product.category = category;
                product.available = true;
                product.published = true;

                products.add(product);

                i++;
            }
        } catch (Exception e){
            Logger.error("Error while parsing " + url +" ; "+ e.getMessage());
        }
        return  products;
    }

    public List<String> productUrls () throws IOException {
        List<String> sectionLinks = getSectionsUrls();
        List<String> categoriesLinks = new ArrayList<>();
        List<String> productLinks = new ArrayList<>();

        for (String sectionLink : sectionLinks){
            categoriesLinks.addAll(getCategoryUrls(sectionLink));
        }

        for (String categoryLink : categoriesLinks){
            productLinks.addAll(getProductUrls(categoryLink));
        }

        return productLinks;
    }

    private List<String> getSectionsUrls() throws IOException {
        List<String> urls = new ArrayList<>();

        Document doc = Util.connect(SITE_URL + "/ru/production/catalog/");

        Elements sections = doc.select(".nav > li > a");

        for (Element section : sections){
            String sectionUrl = section.attributes().get("href");
            urls.add(SITE_URL + sectionUrl);
        }
        return urls;
    }

    private List<String> getCategoryUrls(String sectionUrl) throws IOException {
        List<String> urls = new ArrayList<>();

        if (sectionUrl.equals(""))
            return urls;

        Document sectionPage = Util.connect(sectionUrl);

        Elements categories = null;
        if (!sectionPage.select(".cat_onelevel").isEmpty())
            categories = sectionPage.select(".cat_onelevel").first().children();
        else
            categories = sectionPage.select(".cat").first().children();

        for (Element category : categories){
            String categoryUrl = category.child(0).attributes().get("href");
            urls.add(SITE_URL + categoryUrl);
        }

        return urls;
    }

    private List<String> getProductUrls(String categoryUrl) throws IOException {
        List<String> urls = new ArrayList<>();

        if (categoryUrl.equals(""))
            return urls;

        Document categoryPage = Util.connect(categoryUrl);

        if (!categoryPage.select(".cat_onelevel").isEmpty()){
            urls.add(categoryUrl);
            return urls;
        }

        Elements products = categoryPage.select(".cat").get(1).children();
        for (Element product : products){
            String productUrl = product.child(0).attributes().get("href");
            urls.add(SITE_URL + productUrl);
        }

        return urls;
    }

}
