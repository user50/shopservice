package controllers;

import com.shopservice.ProductConditions;
import com.shopservice.assemblers.ProductAssembler;
import com.shopservice.dao.Group2ProductRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.shopservice.MServiceInjector.injector;

public class ProductController extends Controller {

    private static Group2ProductRepository group2ProductRepository = injector.getInstance(Group2ProductRepository.class);
    private static ProductAssembler productAssembler = injector.getInstance(ProductAssembler.class);

    public static Result getProductsPage(String clientId, Long groupId,  String categoryId, String words, Long offset, Long limit) throws Exception {
        ProductConditions conditions = new ProductConditions();
        conditions.categoryId = categoryId;

        if (words != null)
            conditions.words = Arrays.asList(words.trim().split(" "));

        conditions.offset = offset.intValue();
        conditions.limit = limit.intValue();

        return ok(Json.toJson(productAssembler.getProductsPage(clientId, groupId.intValue(), conditions)));
    }

    public static Result updateProduct(String clientId, Long groupId, String productId)
    {
        Map<String,Boolean> body = Json.fromJson(request().body().asJson(), Map.class);
        if (!body.containsKey("checked"))
            return badRequest("Missing field checked");

        group2ProductRepository.set(productId, groupId.intValue(), body.get("checked"));

        return ok();
    }

    public static Result updateProducts( String clientId, Long groupId, String categoryId, boolean checked ) throws IOException {
        if (categoryId != null)
        {
            group2ProductRepository.set(clientId, categoryId, groupId.intValue(), checked);
        }
        else
        {
            List<String> productEntries = Json.fromJson(request().body().asJson(), List.class);

            group2ProductRepository.set(clientId, groupId.intValue(), productEntries, checked);
        }

        return ok();
    }
}
