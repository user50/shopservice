package com.shopservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CategoryEntry {
    @Id
    public String id;
    public String categoryId;
    public String categoryName;

}
