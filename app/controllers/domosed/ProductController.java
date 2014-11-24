package controllers.domosed;

import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.domosed.DomosedProductRepository;
import com.shopservice.transfer.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 19.11.14
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class ProductController extends Controller {

    private static DomosedProductRepository productRepository = MServiceInjector.injector.getInstance(DomosedProductRepository.class);

    public static Result get(String words, Long offset, Long limit) throws SQLException {
        if (words == null || words.equals(""))
            return badRequest();

        ProductConditions conditions = new ProductConditions();
        conditions.words = Arrays.asList(words.trim().split(" "));
        conditions.offset = offset.intValue();
        conditions.limit = limit.intValue();

        return ok(Json.toJson(productRepository.get(conditions)));
    }

    public static Result update(String id) throws SQLException {
        Product body = Json.fromJson(request().body().asJson(), Product.class );

        if (!id.equals(body.id))
            return badRequest();

        return ok(Json.toJson(productRepository.update(body)));
    }
}
