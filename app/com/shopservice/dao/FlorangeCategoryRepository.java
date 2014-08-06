package com.shopservice.dao;

import com.shopservice.domain.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class FlorangeCategoryRepository implements CategoryRepository {
    public static final String SITE_URL = "http://florange.ua";

    @Override
    public List<Category> getCategories() throws Exception {
        List<Category> categories = new ArrayList<>();

        Document doc = Jsoup.connect(SITE_URL + "/ru/production/catalog/").get();

        Elements categoryElements = doc.select(".nav > li > a");

        for (Element categoryL : categoryElements){
            Category category = new Category();
            String categoryName = categoryL.attributes().get("title");
            category.id = String.valueOf(categoryName.hashCode());
            category.name = categoryName;
            categories.add(category);
        }

        return categories;
    }

    @Override
    public Set<Category> getParents(Collection<String> categoryIds) throws Exception {
        return new HashSet<>();
    }
}
