package com.shopservice.domain;

import com.avaje.ebean.Ebean;
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Site> sites = new ArrayList<Site>();
}
