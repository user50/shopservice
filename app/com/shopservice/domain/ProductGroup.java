package com.shopservice.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopservice.PriceListType;
import com.shopservice.Util;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.exception.Description;
import com.shopservice.exception.ValidationException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.shopservice.MServiceInjector.injector;

@Entity
public class ProductGroup {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer id;
    public String name;

    public PriceListType format;

    public Currency regionalCurrency;

    public Currency productCurrency;

    public String rate;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    public List<Group2Product> checks = new ArrayList<Group2Product>();


}
