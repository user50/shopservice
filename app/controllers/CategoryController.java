package controllers;

import com.shopservice.Services;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CategoryController extends Controller {

    public static Result getCategories(String clientId, String groupId) throws Exception {
        Object response = Services.getCategoryAssembler(clientId).getCategories(clientId, groupId);

        return ok(Json.toJson(response));
    }
}
