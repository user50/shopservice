package controllers.domosed;

import com.shopservice.MServiceInjector;
import com.shopservice.domosed.Manufacturer;
import com.shopservice.domosed.ManufacturerRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

/**
 * Created by user50 on 18.11.2014.
 */
public class ManufacturerController extends Controller {

    private static ManufacturerRepository manufacturerRepository = MServiceInjector.injector.getInstance(ManufacturerRepository.class);

    public static Result get() throws SQLException {
        return ok(Json.toJson(manufacturerRepository.list()));
    }

    public static Result update(String id) throws SQLException {
        Manufacturer body = Json.fromJson(request().body().asJson(), Manufacturer.class );

        if (!id.equals(body.id))
            return badRequest();

        return ok(Json.toJson(manufacturerRepository.update(body)));
    }

    public static Result calculatePrices(String id) throws SQLException {
        if(id == null || id.equals(""))
            return badRequest();

        manufacturerRepository.calculatePrices(manufacturerRepository.find(id));

        return ok();
    }
}
