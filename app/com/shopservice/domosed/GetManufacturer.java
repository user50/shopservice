package com.shopservice.domosed;

import com.shopservice.queries.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetManufacturer implements Query<Manufacturer> {
    private String id;

    public GetManufacturer(String id) {
        this.id = id;
    }

    @Override
    public Manufacturer fill(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.id = resultSet.getString("manufacturers_id");
        manufacturer.name = resultSet.getString("manufacturers_name");
        manufacturer.rate = resultSet.getDouble("manufacturers_rate");

        return manufacturer;
    }

    @Override
    public String getRawSql() {
        return "SELECT * FROM manufacturers WHERE manufacturers_id = ? ";
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        statement.setString(1, id);
    }
}
