package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.CategoryEntry;
import com.shopservice.domain.ClientSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;

public class CategoryEntryController extends Controller {

    public static Result getCategoryEntries(String clientId)
    {
        return ok(Json.toJson( Ebean.find(CategoryEntry.class).findList() ));
    }

    public static Result createCategoryEntry(String clientId)
    {
        ClientSettings settings = ClientSettings.findById(clientId);
        if (settings == null)
            return status(404, "Nonexistent client id");

        CategoryEntry categoryEntry = Json.fromJson(request().body().asJson(), CategoryEntry.class);
        categoryEntry.id = UUID.randomUUID().toString();

        settings.categoryEntries.add(categoryEntry);
        Ebean.save(settings);

        return ok(Json.toJson(categoryEntry));
    }

    public static Result deleteCategoryEntry(String clientId, String categoryEntryId)
    {
        Ebean.delete(CategoryEntry.class, categoryEntryId);

        return ok();
    }

}
