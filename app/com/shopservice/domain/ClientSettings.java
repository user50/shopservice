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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductEntry> productEntries = new ArrayList<ProductEntry>();

    public static ClientSettings findById(String id)
    {
        return Ebean.find(ClientSettings.class, id);
    }

    public static List<ClientSettings> getAll()
    {
        return Ebean.find(ClientSettings.class).findList();
    }

    public static void remove(String id)
    {
        Ebean.delete(ClientSettings.class, id);
    }

    public void save()
    {
        Ebean.save(this);
    }

    public void update()
    {
        Ebean.update(this);
    }

    public static ClientSettings getBySiteName(String siteName) {
        return Ebean.find(ClientSettings.class).where().eq("siteName", siteName).findUnique();
    }

}
