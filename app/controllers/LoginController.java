package controllers;

import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.domain.ClientSettings;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import tyrex.services.UUID;

import static com.shopservice.MServiceInjector.injector;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 28.09.13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class LoginController extends Controller {

    private static final Integer EXPIRATION = 60 * 60 * 24;

    private static ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    public static Result authentication()
    {
        String password = request().body().asFormUrlEncoded().get("j_password")[0];
        String username = request().body().asFormUrlEncoded().get("j_username")[0];

        ClientSettings clientSettings = clientSettingsRepository.getBySiteName(username);

        if (clientSettings == null || !clientSettings.password.equals(password))
            return status(401);

        String key = UUID.create();
        Cache.set(key, clientSettings.id);

        response().setCookie("key", key, EXPIRATION);
        response().setCookie("clientId", clientSettings.id, EXPIRATION);

        return ok();
    }

    public static Result logout()
    {
        Http.Cookie cookie = request().cookie("key");

        if (cookie == null)
            return ok();

        Cache.remove(cookie.value());

        return ok();
    }
}
