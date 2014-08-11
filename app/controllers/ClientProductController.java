package controllers;

import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.assemblers.PaginationResult;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;

public class ClientProductController extends Controller {

    public static Result find(String clientId, String like, String contain, Integer offset, Integer limit)
    {
        ProductConditions conditions = new ProductConditions();
        conditions.offset = offset;
        conditions.limit = limit;

        if (contain != null)
            conditions.words = Arrays.asList(contain.split(" "));

        List response = Services.getProductDAO(clientId).find(conditions);
        int total = Services.getProductDAO(clientId).size(conditions);

        if (total > 20)
            total = 20;

        PaginationResult result = new PaginationResult(total, response);

        return ok(Json.toJson(result));
    }
}
