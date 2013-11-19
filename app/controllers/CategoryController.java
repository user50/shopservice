package controllers;

import com.shopservice.Services;
import com.shopservice.queries.CategoryQuery;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

public class CategoryController extends Controller {

    public static Result getCategories(String clientId) throws Exception {
        Object response = Services.getCategoryDAO(clientId).getCategories();

        return ok(Json.toJson(response));
    }

}
