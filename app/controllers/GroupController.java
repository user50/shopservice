package controllers;

import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class GroupController extends Controller {

    public static Result getGroups(String clientId)
    {
        return ok( Json.toJson( ProductGroup.get(clientId) ) );
    }

    public static Result addGroup( String clientId )
    {
        ClientSettings settings = ClientSettings.findById(clientId);
        if (settings == null)
            return badRequest("Client with id "+clientId+" doesn't exist ");

        ProductGroup productGroup = Json.fromJson( request().body().asJson(), ProductGroup.class );

        if (productGroup.name.isEmpty() )
            return badRequest("Name cannot be empty");

        if (ProductGroup.exist(clientId, productGroup.name))
            return badRequest("Category with specified name already exists");

        settings.productGroups.add(productGroup);

        settings.save();

        return ok( Json.toJson(productGroup) );
    }

}
