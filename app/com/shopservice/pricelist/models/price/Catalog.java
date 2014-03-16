package com.shopservice.pricelist.models.price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 10.11.12
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class Catalog {

    public int count = 1;
    public HashMap<String,Category> categoryHashMap = new HashMap<String, Category>();

    public String getManufacturerId(String categoryName, String manufacturerName){
        if (!categoryHashMap.keySet().contains(categoryName))
            categoryHashMap.put(categoryName,new Category(categoryName));

        if (!categoryHashMap.get(categoryName).containsManufacturer(manufacturerName))
            categoryHashMap.get(categoryName).addManufacturer(manufacturerName);

        return categoryHashMap.get(categoryName).manufacturersMap.get(manufacturerName).id;
    }

    public List<com.shopservice.pricelist.models.price.Category>   getCategories(){
        List<com.shopservice.pricelist.models.price.Category> listCategories = new ArrayList<com.shopservice.pricelist.models.price.Category>();
        for(Category category: categoryHashMap.values()){
            listCategories.add(new com.shopservice.pricelist.models.price.Category(category.id, category.name));
            for(Manufacturer manufacturer: category.manufacturersMap.values())
                listCategories.add(new com.shopservice.pricelist.models.price.Category(manufacturer.id, category.id, manufacturer.name));

        }

        return listCategories;
    }

    private class Manufacturer{

        String name;
        String id;

        private Manufacturer(String name) {
            this.name = name;
            this.id = (count++)+"";
        }
    }

    private  class Category{

        String id;
        String name;
        Map<String,Manufacturer> manufacturersMap = new HashMap<String, Manufacturer>();

        private Category(String name) {
            this.name = name;
            this.id = (count++)+"";
        }

        public void addManufacturer(String manufacturerName){
           manufacturersMap.put(manufacturerName,new Manufacturer(manufacturerName));
        }

        public boolean containsManufacturer(String manufacturerName){
            return manufacturersMap.keySet().contains(manufacturerName);
        }
    }
}
