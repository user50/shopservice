package controllers;

import com.shopservice.dao.Group2ProductRepository;
import com.shopservice.dao.ProductEntryRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

import static com.shopservice.MServiceInjector.injector;

public class ProductController extends Controller {

    private static Group2ProductRepository group2ProductRepository = injector.getInstance(Group2ProductRepository.class);
    private static ProductEntryRepository productEntryRepository = injector.getInstance(ProductEntryRepository.class);


    public static Result getProducts(String clientId,Long siteId, String categoryId) throws Exception {
        return ok(Json.toJson(productEntryRepository.findAndRefresh(clientId, categoryId, siteId.intValue()) ));
    }

    public static Result updateProduct(String clientId, Long siteId, String productId)
    {
        Map<String,Boolean> body = Json.fromJson(request().body().asJson(), Map.class);
        if (!body.containsKey("checked"))
            return badRequest("Missing field checked");

        group2ProductRepository.set(productId, siteId.intValue(), body.get("checked"));

        return ok();
    }

    public static Result updateProducts(String clientId, Long siteId, String categoryId)
    {
        Map<String,Boolean> body = Json.fromJson(request().body().asJson(), Map.class);
        if (!body.containsKey("checked"))
            return badRequest("Missing field checked");

        group2ProductRepository.set(clientId, categoryId, siteId.intValue(), body.get("checked"));

        return ok();
    }
}
