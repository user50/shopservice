package com.shopservice.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
public class ClientSettings {

    @Id
    public String id;
    public String siteName;
    public String siteUrl;
    public String databaseUrl;
    public String pathToProductPage;
    public String pathToProductImage;
    public String password;
    public String encoding;
    public Currency currency;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductGroup> productGroups = new ArrayList<ProductGroup>();
}
