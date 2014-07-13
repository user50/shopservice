package com.shopservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by user50 on 13.07.2014.
 */

@Entity
@Access(AccessType.FIELD)
public class LinkedProductEntry {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer id;

    public String name;

    @Transient
    public String clientProductsName;

    @Transient
    public String productEntryId;

    @JsonIgnore
    @ManyToOne
    public ProductProvider productProvider;

    @JsonIgnore
    @ManyToOne
    public ProductEntry productEntry;


}
