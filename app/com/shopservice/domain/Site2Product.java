package com.shopservice.domain;

import com.avaje.ebean.Ebean;

import javax.persistence.*;

@Entity
public class Site2Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public boolean checked;

    @ManyToOne
    public Site site;

    @ManyToOne
    public ProductEntry productEntry;

    public Site2Product() {
    }

    public Site2Product(Site site) {
        this.site = site;
    }

    public static Site2Product get(String productId, int siteId)
    {
        return Ebean.find(Site2Product.class).where().eq("product_entry_id", productId ).eq("site_id", siteId).findUnique();
    }

    public static void set(String productId, int siteId, Boolean checked) {

        Site2Product site2Product = Site2Product.get( productId, siteId );
        if (site2Product == null)
            site2Product = new Site2Product();

        site2Product.checked = checked;
        site2Product.site = Ebean.find(Site.class, siteId);
        site2Product.productEntry = Ebean.find(ProductEntry.class, productId);

        Ebean.save(site2Product);
    }

    public static void set(String clientId, String categoryId, int siteId, Boolean checked) {

        for (ProductEntry productEntry : ProductEntry.get(clientId, categoryId))
            set(productEntry.id, siteId, checked);

    }
}
