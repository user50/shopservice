package com.shopservice.productsources;


import com.shopservice.transfer.Product;

import java.util.List;

public interface ProductSource {
    public List<Product> get(Integer providerId );
}
