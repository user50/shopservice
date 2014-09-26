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

    @Transient
    public String clientProductId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_provider_id")
    public ProductProvider productProvider;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_entry_id")
    public ProductEntry productEntry;


}
