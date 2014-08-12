package com.shopservice.urlgenerate;

import com.shopservice.transfer.Product;

public class MebelAlliance extends UrlGenerator {

    @Override
    public String getClientId() {
        return "client2";
    }

    @Override
    public String generateProductUrl(Product product) {
        return "http://mebel-alliance.kiev.ua/index.php?route=product/product&path="
                + product.category.parentId + "_" + product.category.id
                + "&product_id=" + product.id;
    }

    @Override
    public String generateProductImageUrl(Product product) {
        return "http://mebel-alliance.kiev.ua/image/" + product.imageUrl;
    }
}
