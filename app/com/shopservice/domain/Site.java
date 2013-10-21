package com.shopservice.domain;

import com.avaje.ebean.Ebean;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Site {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public int id;
    public String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Site2Product> checks;

    public static List<Site> get(String clientId) {
        return Ebean.find(Site.class).where().eq("client_settings_id", clientId).findList();
    }

    public void save() {
        Ebean.save(this);
    }
}
