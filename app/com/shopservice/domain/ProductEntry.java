package com.shopservice.domain;

import com.avaje.ebean.Ebean;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class ProductEntry {

    @Id
    public String id;
    public String productId;
    public String productName;

    public static List<ProductEntry> find(String clientSettingsId)
    {
        return Ebean.find(ProductEntry.class).where().eq("client_settings_id", clientSettingsId).findList();
    }

}
