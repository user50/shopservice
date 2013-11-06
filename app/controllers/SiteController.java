package controllers;

import com.shopservice.domain.Site;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SiteController extends Controller {

    public static Result getSites()
    {
        return ok( Json.toJson( Site.get() ) );
    }

}
