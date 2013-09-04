package controllers;

import com.shopservice.Services;
import com.shopservice.queries.ProductQueryByCategories;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.Arrays;

public class ProductController extends Controller {

    public static Result getProducts(String clientId, String categoryId) throws SQLException {
        Object response = Services.getDataBaseManager(clientId).executeQueryForList(new ProductQueryByCategories(clientId, Arrays.asList(categoryId)));

        return ok(Json.toJson(response));
    }
}
