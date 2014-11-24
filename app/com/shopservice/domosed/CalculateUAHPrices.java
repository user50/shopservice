package com.shopservice.domosed;

import com.shopservice.queries.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CalculateUAHPrices implements Update {
    private Manufacturer manufacturer;

    public CalculateUAHPrices(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getRawSql() {
        return "UPDATE `products` \n" +
                "SET `products_price` = products_usd_price * ?\n" +
                "WHERE manufacturers_id = ? AND products_usd_price is not null AND products_usd_price > 0";
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setDouble(1, manufacturer.rate);
        statement.setString(2, manufacturer.id);
    }
}
