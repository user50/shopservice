package controllers;

import play.mvc.*;

import java.sql.SQLException;

public class Application extends Controller {
  
  public static Result index() throws SQLException {
      return ok(index.render("Your new application is ready."));
  }
  
}