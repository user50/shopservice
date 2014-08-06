package com.shopservice;

import com.shopservice.dao.FlorangeCategoryRepository;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;
import com.shopservice.productsources.Florange;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import play.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 05.08.2014.
 */
public class FlorangeTest {
    public static final String SITE_URL = "http://florange.ua";
    @Test
    public void testName() throws Exception {
        List<Product> products = new ArrayList<>();

        for (String productUrl : productUrls()){
            products.addAll(getProducts(productUrl));
        }

        System.out.println();
    }

    private List<Product> getProducts(String url) throws IOException {
        System.out.println("Parsing of the " + url);
        List<Product> products = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url).get();

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

                String id = tabLi.id();
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
                products.add(product);

                i++;
            }
        } catch (Exception e){
            Logger.error("Error while parsing " + url);
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

        Document doc = Jsoup.connect(SITE_URL + "/ru/production/catalog/").get();

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

        Document sectionPage = Jsoup.connect(sectionUrl).get();
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

        Document categoryPage = Jsoup.connect(categoryUrl).get();

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

    @Test
    public void testName1() throws Exception {
        List<Product> products = getProducts("http://florange.ua/ru/production/clutch/jour_d_ete/");
    }

    @Test
    public void testProductsUrl() throws Exception {
        productUrls();
    }

    @Test
    public void testGet() throws Exception {
        Florange florangeProductSource = new Florange();
        List<Product> products = florangeProductSource.get(null);
        System.out.println();
    }

    @Test
    public void testGetCategories() throws Exception {
        FlorangeCategoryRepository categoryRepository = new FlorangeCategoryRepository();
        System.out.println(categoryRepository.getCategories());
    }
}
