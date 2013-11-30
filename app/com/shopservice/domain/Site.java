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

}
