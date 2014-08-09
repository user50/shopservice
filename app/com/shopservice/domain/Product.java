package com.shopservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mongodb.DBObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    public Product() {
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String id;
    public String model;
    public String manufacturer;
    public String name;
    public String sku;
    public boolean available;
    public double price;
    public String shortDescription;
    public String description;
    public String warranty;
    public String url;
    public String imageUrl;
    public Boolean published;
    public Category category;

    public boolean checked;
}
