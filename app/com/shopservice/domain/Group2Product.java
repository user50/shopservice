package com.shopservice.domain;

import javax.persistence.*;

@Entity(name = "group2product")
@Access(AccessType.FIELD)
public class Group2Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    public ProductGroup productGroup;

    @ManyToOne
    @JoinColumn(name = "product_entry_id")
    public ProductEntry productEntry;

    public Group2Product() {
    }

    public Group2Product(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
}
