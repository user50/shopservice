package com.shopservice.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shopservice.transfer.Category;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Product {


    public String id;
    public String model;
    public String manufacturer;
    public String name;
    public String sku;
    public boolean available;
    public double price;
    public double usdPrice;
    public String shortDescription;
    public String description;
    public String warranty;
    public String url;
    public String imageUrl;
    public Boolean published;
    public Category category;

    public Product(String name, double price, Boolean published ) {
        this.name = name;
        this.price = price;
        this.published = published;
    }

    public Product() {
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
