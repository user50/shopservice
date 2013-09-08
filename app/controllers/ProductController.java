package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ProductEntry;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

public class ProductController extends Controller {

    public static Result getProducts(String clientId, String categoryId) throws SQLException {
        return ok(Json.toJson( ProductEntry.find(clientId, categoryId) ));
    }

    public static Result updateProduct(String clientId, String categoryId, String productId, Boolean checked)
    {
        Ebean.createSqlUpdate("UPDATE product_entry SET `checked`=:checked " +
                "WHERE id=:id")
                .setParameter("checked", checked)
                .setParameter("id", productId).execute();
        return ok();
    }

    public static Result updateProducts(String clientId, String categoryId, Boolean checked)
    {
        Ebean.createSqlUpdate("UPDATE product_entry SET `checked`=:checked " +
                "WHERE client_settings_id = :clientSettings AND category_id = :categoryId")
                .setParameter("checked", checked)
                .setParameter("clientSettings", clientId)
                .setParameter("categoryId", categoryId).execute();

        return ok();
    }
}
