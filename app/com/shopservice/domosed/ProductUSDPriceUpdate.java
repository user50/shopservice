package com.shopservice.domosed;

import com.shopservice.queries.Update;
import com.shopservice.transfer.Product;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 20.11.14
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class ProductUSDPriceUpdate implements Update {
    private Product product;

    public ProductUSDPriceUpdate(Product product) {
        this.product = product;
    }

    @Override
    public String getRawSql() {
        return "UPDATE products SET products_usd_price = ? WHERE products_id = ?";
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setDouble(1, product.usdPrice);
        statement.setString(2, product.id);
    }
}
