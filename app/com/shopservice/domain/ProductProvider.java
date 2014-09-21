package com.shopservice.domain;

import javax.persistence.*;

@Entity(name = "product_provider")
@Access(AccessType.FIELD)
public class ProductProvider {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer id;

    public String name;

    public double margin;

    public String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_settings_id")
    public ClientSettings clientSettings;
}
