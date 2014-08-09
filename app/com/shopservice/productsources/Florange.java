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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user50 on 06.08.2014.
 */
public class Florange implements ProductSource {
    public static final String SITE_URL = "http://florange.ua";
    private static final Map<Integer, Integer> descIndexes;
    static
    {
        descIndexes = new HashMap<>();
        descIndexes.put(0, 1);
        descIndexes.put(1, 3);
        descIndexes.put(2, 5);
        descIndexes.put(3, 7);
        descIndexes.put(4, 9);
        descIndexes.put(5, 11);
        descIndexes.put(6, 13);
    }
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

    public List<Product> getProducts(String url) throws IOException {
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
                String title = tabImg.attributes().get("title");
                String productName = title + " " + generalName;

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
                product.description = getDescription(doc, title);
                product.url = url;
                product.imageUrl = SITE_URL + imageUrl;
                product.category = category;
                product.available = true;
                product.published = true;

                products.add(product);

                System.out.println("Product: " + productName + " is added. " +
                        ((product.description != null) ? "With description" : "Without description"));
                i++;
            }
        } catch (Exception e){
            Logger.error("Error while parsing " + url +" ; "+ e.getMessage());
        }
        return  products;
    }

    private String getDescription(Document page, String title){
        Elements descElements = page.select(".description > p");
        Elements descTitles = page.select(".description > p > strong");

        int descIndex = -1;

        for (Element descTitle: descTitles){
            if (descTitle.text().toLowerCase().contains(title.toLowerCase())){
                descIndex = descTitles.indexOf(descTitle);
                break;
            }

            if (title.split("\\s+")[0].toLowerCase().contains(descTitle.text().toLowerCase())){
                descIndex = descTitles.indexOf(descTitle);
                break;
            }

        }

        if (descIndex != -1)
            return descElements.get(descIndexes.get(descIndex)).text();

        return null;
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
