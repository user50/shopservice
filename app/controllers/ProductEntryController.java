package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductEntry;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;

public class ProductEntryController extends Controller {

    public static Result getProductIdEntities(String clientId)
    {
        return ok(Json.toJson(ProductEntry.find(clientId)));
    }

    public static Result createProductIdEntry(String clientId)
    {
        ClientSettings settings = ClientSettings.findById(clientId);
        if (settings == null)
            return status(404, "Nonexistent client id");

        ProductEntry productEntry = Json.fromJson(request().body().asJson(),ProductEntry.class);
        productEntry.id = UUID.randomUUID().toString();

        settings.productEntries.add(productEntry);
        Ebean.save(settings);

        return ok(Json.toJson(productEntry));
    }

    public static Result updateProductIdEntry(String clientId, String productEntryId)
    {
        return TODO;
    }

    public static Result deleteProductIdEntry(String clientId, String productEntryId)
    {
        Ebean.delete(ProductEntry.class, productEntryId);

        return ok();
    }

}
