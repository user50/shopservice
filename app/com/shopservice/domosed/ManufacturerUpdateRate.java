package com.shopservice.domosed;

import com.shopservice.queries.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 19.11.14
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class ManufacturerUpdateRate implements Update {
    private Manufacturer manufacturer;

    public ManufacturerUpdateRate(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
    }
    @Override
    public String getRawSql() {
        return "UPDATE manufacturers SET manufacturers_rate = ? WHERE manufacturers_id = ?";
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setDouble(1, manufacturer.rate);
        statement.setString(2, manufacturer.id);
    }
}
