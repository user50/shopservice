package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.Services;
import com.shopservice.domain.ProductEntry;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

import static com.shopservice.Services.productEntryService;

public class ProductController extends Controller {

    public static Result getProducts(String clientId, String categoryId) throws SQLException {
        return ok(Json.toJson( ProductEntry.find(clientId, categoryId) ));
    }

    public static Result updateProduct(String clientId, String categoryId, String productId, Boolean checked)
    {
        productEntryService.setChecked(checked, productId);

        return ok();
    }

    public static Result updateProducts(String clientId, String categoryId, Boolean checked)
    {
        productEntryService.setChecked(checked, clientId, categoryId);

        return ok();
    }
}
