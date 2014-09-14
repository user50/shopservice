package com.shopservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 14.09.2014.
 */
@Entity
@Access(AccessType.FIELD)
public class ClientsCategory {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer id;

    public String name;

    public Integer parentId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductEntry> productEntries = new ArrayList<ProductEntry>();
}
