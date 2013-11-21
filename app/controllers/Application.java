package controllers;

import com.shopservice.Authentication;
import play.mvc.*;

import views.html.*;

import java.sql.SQLException;

public class Application extends Controller {

    @With(Authentication.class)
    public static Result index() throws SQLException {
        return ok(price.render());
    }

}