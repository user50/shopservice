package com.shopservice.domain;

public class Category {
    public String id;
    public String name;

    @Override
    public String toString() {
        return "Category Id: "+id+", Category Name: "+name;
    }
}
