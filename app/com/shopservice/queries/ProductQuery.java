package com.shopservice.queries;

import com.shopservice.Services;
import com.shopservice.Util;
import com.shopservice.domain.Category;
import com.shopservice.domain.Product;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class ProductQuery implements Query<Product> {
    protected String clientId;

    protected ProductQuery(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Product fill(ResultSet resultSet) throws SQLException {
        Product product = new Product();

        product.id = resultSet.getString("id");
        product.manufacturer = resultSet.getString("manufacturer");
        product.name = resultSet.getString("name");
        product.price = resultSet.getDouble("price");
        //product.available = resultSet.getBoolean("available");
        product.shortDescription = resultSet.getString("shortDescription");
        product.description = resultSet.getString("description");
        //product.warranty = resultSet.getString("warranty");
        product.url = resultSet.getString("url");
        product.imageUrl = resultSet.getString("imageUrl");
        product.published = resultSet.getBoolean("published");

        Category category = new Category();
        category.id = resultSet.getString("categoryId");
        category.name = resultSet.getString("categoryName");
        category.parentId = resultSet.getString("categoryParentId");

        product.category = category;

        product.url = Services.getUrlGenerator(clientId).generateProductUrl(product);
        product.imageUrl = Services.getUrlGenerator(clientId).generateProductImageUrl(product);

        return product;
    }
}
