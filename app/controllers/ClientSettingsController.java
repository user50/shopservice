package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import tyrex.services.UUID;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientSettingsController extends Controller {

    public static Result getClientSettings()
    {
        return ok(Json.toJson(Services.getClientSettingsDAO().getAll()));
    }

    public static Result getClientSetting(String id) throws SQLException {
        Object response = Services.getClientSettingsDAO().findById(id);

        if (response == null)
            return status(404);

        return ok(Json.toJson(response));
    }

    public static Result createClientSettings() throws SQLException {
        ClientSettings clientSettings = Json.fromJson(request().body().asJson(), ClientSettings.class);

        if (clientSettings.id == null)
            clientSettings.id = UUID.create();
        Services.getClientSettingsDAO().save(clientSettings);

        return ok(Json.toJson(clientSettings));
    }

    public static Result updateClientSettings(String id) throws SQLException {
        ClientSettings clientSettings = Json.fromJson(request().body().asJson(), ClientSettings.class);
        clientSettings.id = id;
        Services.getClientSettingsDAO().update(clientSettings);

        return ok();
    }

    public static Result deleteClientSettings(String id) throws SQLException {
        Services.getClientSettingsDAO().remove(id);

        return ok();
    }

}
