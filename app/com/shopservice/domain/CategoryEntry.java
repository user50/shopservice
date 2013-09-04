package com.shopservice.domain;

import com.avaje.ebean.Ebean;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class CategoryEntry {
    @Id
    public String id;
    public String categoryId;
    public String categoryName;

    public static List<CategoryEntry> find(String clientId)
    {
        return Ebean.find(CategoryEntry.class).where().eq("client_settings_id", clientId).findList();
    }
}
