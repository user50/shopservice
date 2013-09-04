package controllers;

import com.shopservice.Services;
import com.shopservice.queries.CategoryQuery;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

public class CategoryController extends Controller {

    public static Result getCategories(String clientId) throws SQLException {
        Object response = Services.getDataBaseManager(clientId).executeQueryForList(new CategoryQuery(clientId));

        return ok(Json.toJson(response));
    }

}
