package com.shopservice.domain;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class ProductProvider {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer id;

    public String name;

    public String url;
}
