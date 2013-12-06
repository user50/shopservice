package com.shopservice.domain;

public class Category {
    public String id;
    public String name;
    public String parentId;

    public int count;

    public Category(String id) {
        this.id = id;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category Id: "+id+", Category Name: "+name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
