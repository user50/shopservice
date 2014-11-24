package controllers;

import com.shopservice.Authentication;
import com.shopservice.InitDemoAccount;
import play.mvc.*;

import views.html.*;

import java.sql.SQLException;

@With(InitDemoAccount.class)
public class Application extends Controller {

    public static Result index() throws SQLException {
        return ok(index.render());
    }

    @With(Authentication.class)
    public static Result price() throws SQLException {
        return ok(price.render());
    }

    @With(Authentication.class)
    public static Result priceUpdated() throws SQLException {
        return ok(priceUpdated.render());
    }

    @With(Authentication.class)
    public static Result actualization() throws SQLException {
        return ok(actualization.render());
    }

    @With(Authentication.class)
    public static Result currencies() throws SQLException {
        return ok(currencies.render());
    }

}