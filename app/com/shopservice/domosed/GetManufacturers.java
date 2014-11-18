package com.shopservice.domosed;

import com.shopservice.queries.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by user50 on 18.11.2014.
 */
public class GetManufacturers implements Query<Manufacturer> {
    @Override
    public Manufacturer fill(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.id = resultSet.getString("manufacturers_id");
        manufacturer.name = resultSet.getString("manufacturers_name");

        return manufacturer;
    }

    @Override
    public String getRawSql() {
        return "SELECT * FROM manufacturers";
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {

    }
}
