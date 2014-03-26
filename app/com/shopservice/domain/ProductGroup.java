package com.shopservice.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductGroup {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public int id;
    public String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Group2Product> checks = new ArrayList<Group2Product>();

}
