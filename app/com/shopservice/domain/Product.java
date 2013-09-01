package com.shopservice.domain;

public class Product {
    public String id;
    public String model;
    public String categoryName;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
