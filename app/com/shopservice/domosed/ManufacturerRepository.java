package com.shopservice.domosed;

import com.shopservice.Services;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user50 on 18.11.2014.
 */
public class ManufacturerRepository {

    public List<Manufacturer> get() throws SQLException {
        return Services.getDataBaseManager("client1").executeQueryForList(new GetManufacturers());
    }

    public Manufacturer update(Manufacturer manufacturer) throws SQLException {
        Services.getDataBaseManager("client1").executeUpdate(new ManufacturerUpdateRate(manufacturer));
        return manufacturer;
    }
}
