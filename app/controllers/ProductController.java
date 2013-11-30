package controllers;

import com.shopservice.MServiceInjector;
import com.shopservice.Services;
import com.shopservice.dao.Group2ProductRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import static com.shopservice.MServiceInjector.injector;

public class ProductController extends Controller {

    private static Group2ProductRepository group2ProductRepository = injector.getInstance(Group2ProductRepository.class);

    public static Result getProducts(String clientId,Long siteId, String categoryId) throws Exception {
        return ok(Json.toJson(Services.getProductEntryRepository().findAndRefresh(clientId, categoryId, siteId.intValue()) ));
    }

    public static Result updateProduct(String clientId, Long siteId, String categoryId, String productId, Boolean checked)
    {
        group2ProductRepository.set(productId, siteId.intValue(), checked);

        return ok();
    }

    public static Result updateProducts(String clientId, Long siteId, String categoryId, Boolean checked)
    {
        group2ProductRepository.set(clientId, categoryId, siteId.intValue(), checked);

        return ok();
    }
}
