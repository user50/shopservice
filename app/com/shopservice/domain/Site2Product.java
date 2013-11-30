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
}
