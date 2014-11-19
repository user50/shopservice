package com.shopservice.domosed;

import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.queries.JdbcProductQuery;
import com.shopservice.transfer.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 19.11.14
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class DomosedProductRepository {
    public static final String DOMOSED_CLIENT_ID = "client1";

    public List<Product> get(ProductConditions productConditions) throws SQLException {
        return Services.getDataBaseManager(DOMOSED_CLIENT_ID)
                .executeQueryForList(new JdbcProductQuery(DOMOSED_CLIENT_ID, productConditions));
    }
}
