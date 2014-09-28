package controllers;

import com.shopservice.ProductConditions;
import com.shopservice.assemblers.ProductAssembler;
import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.dao.Group2ProductRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.domain.ProductEntry;
import com.shopservice.transfer.Product;
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
    private static ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);
    private static ProductEntryRepository productEntryRepository = injector.getInstance(ProductEntryRepository.class);


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
        ProductEntry entryFromBody = Json.fromJson(request().body().asJson(), ProductEntry.class);
        if ( entryFromBody.checked == null)
            return badRequest("Missing field checked");

        entryFromBody.clientSettings = clientSettingsRepository.get(clientId);

        ProductEntry entryFromDb = productEntryRepository.find(entryFromBody.id);
        entryFromDb.description = entryFromBody.description;
        entryFromDb.customCategoryId = entryFromBody.customCategoryId;

        productEntryRepository.update(entryFromDb);

        group2ProductRepository.set(productId, groupId.intValue(), entryFromBody.checked);

        return ok(Json.toJson(entryFromBody));
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
