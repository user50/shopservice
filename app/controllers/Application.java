package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.ClientSettings;
import play.*;
import play.db.DB;
import play.mvc.*;

import views.html.*;

import java.sql.SQLException;

public class Application extends Controller {
  
  public static Result index() throws SQLException {
      return ok(index.render("Your new application is ready."));
  }
  
}