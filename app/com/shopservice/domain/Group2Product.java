package com.shopservice.domain;

import javax.persistence.*;

@Entity
public class Group2Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public boolean checked;

    @ManyToOne
    public ProductGroup productGroup;

    @ManyToOne
    public ProductEntry productEntry;

    public Group2Product() {
    }

    public Group2Product(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
}
