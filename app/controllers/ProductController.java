package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.Site2Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

public class ProductController extends Controller {

    private static int siteId = 1;

    public static Result getProducts(String clientId,Long siteId, String categoryId) throws SQLException {
        return ok(Json.toJson( ProductEntry.findAndRefresh(clientId, categoryId, siteId.intValue()) ));
    }

    public static Result updateProduct(String clientId, Long siteId, String categoryId, String productId, Boolean checked)
    {
        Site2Product.set(productId, siteId.intValue(), checked);

        return ok();
    }

    public static Result updateProducts(String clientId, Long siteId, String categoryId, Boolean checked)
    {
        Site2Product.set(clientId, categoryId, siteId.intValue(), checked);

        return ok();
    }
}
