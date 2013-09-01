package controllers;

import com.shopservice.domain.ProductIdEntry;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

public class ProductIdEntryController extends Controller {
    public static Result getProductIdEntities(String clientId)
    {
        return ok(Json.toJson(ProductIdEntry.find(clientId)));
    }

    public static Result updateProductIdEntries(String clientId)
    {
        List<String>  productIds = Json.fromJson(request().body().asJson(),ArrayList.class);
        ProductIdEntry.update(clientId, productIds);

        return ok();
    }
}
