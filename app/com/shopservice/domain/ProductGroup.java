package com.shopservice.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductGroup {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public int id;
    public String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Group2Product> checks;

}
