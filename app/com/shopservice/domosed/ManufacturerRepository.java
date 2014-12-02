package com.shopservice.domosed;

import com.shopservice.Services;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user50 on 18.11.2014.
 */
public class ManufacturerRepository {
    public static final String DOMOSED_CLIENT_ID = "client1";

    public Manufacturer find(String id) throws SQLException {
        return Services.getDataBaseManager(DOMOSED_CLIENT_ID).executeQueryForOne(new GetManufacturer(id));
    }

    public List<Manufacturer> list() throws SQLException {
        return Services.getDataBaseManager(DOMOSED_CLIENT_ID).executeQueryForList(new GetManufacturers());
    }

    public Manufacturer update(Manufacturer manufacturer) throws SQLException {
        Services.getDataBaseManager(DOMOSED_CLIENT_ID).executeUpdate(new ManufacturerUpdateRate(manufacturer));
        return manufacturer;
    }

    public void calculatePrices(Manufacturer manufacturer) throws SQLException {
        Services.getDataBaseManager(DOMOSED_CLIENT_ID).executeUpdate(new CalculateUAHPrices(manufacturer));
    }
}
