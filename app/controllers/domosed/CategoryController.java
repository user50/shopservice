package controllers.domosed;

import com.shopservice.Services;
import com.shopservice.dao.CategoryRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Yevhen on 1/6/15.
 */
public class CategoryController extends Controller {
    private static CategoryRepository categoryRepository = Services.getCategoryDAO("client1");

    public static Result get() throws Exception {
        return ok(Json.toJson(categoryRepository.getCategories()));
    }
}
