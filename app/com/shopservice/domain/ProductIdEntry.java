package com.shopservice.domain;

import com.avaje.ebean.Ebean;
import tyrex.services.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductIdEntry {

    @Id
    public String id;
    public String productId;

    public ProductIdEntry() {
    }

    public ProductIdEntry(String id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    public static List<String> find(String clientSettingsId)
    {
        List<String> productIds = new ArrayList<String>();
        for (ProductIdEntry entry : Ebean.find(ProductIdEntry.class).
                where().eq("client_settings_id", clientSettingsId).findList()) {
            productIds.add(entry.productId);

        }

        return productIds;
    }

    public static void update(String clientSettingsId, List<String> productIds )
    {
        Ebean.createSqlUpdate("DELETE product_id_entry.* FROM product_id_entry WHERE client_settings_id = :id")
                .setParameter("id", clientSettingsId ).execute();

        ClientSettings settings = ClientSettings.findById(clientSettingsId);

        for (String productId : productIds) {
            settings.productIds.add(new ProductIdEntry(UUID.create(), productId));
        }

        Ebean.save(settings);
    }
}
