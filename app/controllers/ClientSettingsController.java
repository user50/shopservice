package controllers;

import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.domain.ClientSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import tyrex.services.UUID;

import java.sql.SQLException;

import static com.shopservice.MServiceInjector.injector;

public class ClientSettingsController extends Controller {

    private static ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    public static Result getClientSettings()
    {
        return ok(Json.toJson(clientSettingsRepository.get()));
    }

    public static Result getClientSetting(String id) throws SQLException {
        Object response = clientSettingsRepository.get(id);

        if (response == null)
            return status(404);

        return ok(Json.toJson(response));
    }

    public static Result createClientSettings() throws SQLException {
        ClientSettings clientSettings = Json.fromJson(request().body().asJson(), ClientSettings.class);

        if (clientSettings.id == null)
            clientSettings.id = UUID.create();
        clientSettingsRepository.save(clientSettings);

        return ok(Json.toJson(clientSettings));
    }

    public static Result updateClientSettings(String id) throws SQLException {
        ClientSettings clientSettings = Json.fromJson(request().body().asJson(), ClientSettings.class);
        clientSettings.id = id;
        clientSettingsRepository.update(clientSettings);

        return ok();
    }

    public static Result deleteClientSettings(String id) throws SQLException {
        clientSettingsRepository.remove(id);

        return ok();
    }

}
