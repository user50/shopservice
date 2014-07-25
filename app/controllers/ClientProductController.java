package controllers;

import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.domain.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientProductController extends Controller {

    public static Result find(String clientId, String like, String contain)
    {
        ProductConditions conditions = new ProductConditions();

        if (contain != null)
            conditions.words = Arrays.asList(contain.split(" "));

        List response = Services.getProductDAO(clientId).find(conditions);

        if (like != null)
            return ok(Json.toJson(response.subList(0, 10)));

        return ok(Json.toJson(response));
    }
}
