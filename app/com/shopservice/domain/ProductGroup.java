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

@Entity(name = "product_group")
public class ProductGroup {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer id;
    public String name;

    @Enumerated(EnumType.ORDINAL)
    public PriceListType format;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "regional_currency")
    public Currency regionalCurrency;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "product_currency")
    public Currency productCurrency;

    public String rate;

    public boolean useCustomCategories;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_settings_id")
    public ClientSettings clientSettings;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productGroup")
    public List<Group2Product> checks = new ArrayList<Group2Product>();


}
