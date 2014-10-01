package com.shopservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="client_settings")
@Access(AccessType.FIELD)
public class ClientSettings {

    @Id
    public String id;

    @Column(name = "site_name")
    public String siteName;

    @Column(name = "site_url")
    public String siteUrl;

    @Column(name = "database_url")
    public String databaseUrl;

    @Column(name = "path_to_product_page")
    public String pathToProductPage;

    @Column(name = "path_to_product_image")
    public String pathToProductImage;
    public String password;
    public String encoding;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientSettings")
    public List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientSettings")
    public List<ProductGroup> productGroups = new ArrayList<ProductGroup>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientSettings")
    public List<ProductProvider> productProviders = new ArrayList<ProductProvider>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientSettings")
    public List<ClientsCategory> clientsCategories = new ArrayList<ClientsCategory>();
}
