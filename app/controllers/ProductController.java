package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.Services;
import com.shopservice.domain.ProductEntry;
import com.shopservice.domain.Site2Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

public class ProductController extends Controller {

    public static Result getProducts(String clientId,Long siteId, String categoryId) throws Exception {
        return ok(Json.toJson(Services.getProductEntryRepository().findAndRefresh(clientId, categoryId, siteId.intValue()) ));
    }

    public static Result updateProduct(String clientId, Long siteId, String categoryId, String productId, Boolean checked)
    {
        Services.getSite2ProductRepository().set(productId, siteId.intValue(), checked);

        return ok();
    }

    public static Result updateProducts(String clientId, Long siteId, String categoryId, Boolean checked)
    {
        Services.getSite2ProductRepository().set(clientId, categoryId, siteId.intValue(), checked);

        return ok();
    }
}
