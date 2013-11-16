package controllers;

import com.shopservice.domain.Group2Product;
import com.shopservice.domain.ProductEntry;
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
        Group2Product.set(productId, siteId.intValue(), checked);

        return ok();
    }

    public static Result updateProducts(String clientId, Long siteId, String categoryId, Boolean checked)
    {
        Group2Product.set(clientId, categoryId, siteId.intValue(), checked);

        return ok();
    }
}
