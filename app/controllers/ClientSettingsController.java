package controllers;

import com.shopservice.ClientSettingsService;
import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientSettingsController extends Controller {

    private static ClientSettingsService service = Services.CLIENT_SETTINGS_SERVICE;

    public static Result getClientSettings(String id) throws SQLException {
        Object response = service.getClientSettings(id);

        if (response == null)
            return status(404);

        return ok(Json.toJson(service.getClientSettings(id)));
    }

    public static Result createClientSettings() throws SQLException {
        ClientSettings clientSettings = Json.fromJson(request().body().asJson(), ClientSettings.class);

        return ok(service.createClientSettings(clientSettings).id);
    }

    public static Result updateClientSettings(String id) throws SQLException {
        ClientSettings clientSettings = Json.fromJson(request().body().asJson(), ClientSettings.class);
        service.updateClientSettings(clientSettings);

        return ok();
    }

    public static Result deleteClientSettings(String id) throws SQLException {
        service.removeClientSettings(id);

        return ok();
    }

    public static Result getProductIds(String id) throws SQLException {
        return ok(Json.toJson(service.getProductIds(id)));
    }

    public static Result updateProductIds(String id) throws SQLException {
        List<String> products = (List<String>)Json.fromJson(request().body().asJson(), ArrayList.class);
        service.setProductIds(id, products);

        return ok();
    }
}
