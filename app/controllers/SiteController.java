package controllers;

import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SiteController extends Controller {

    public static Result getSites(String clientId)
    {
        return ok( Json.toJson( Services.getProductGroupRepository().get(clientId) ) );
    }

    public static Result addSite( String clientId )
    {
        ClientSettings settings = Services.getClientSettingsDAO().findById(clientId);
        if (settings == null)
            return badRequest("Client with id "+clientId+" doesn't exist ");

        ProductGroup productGroup = Json.fromJson( request().body().asJson(), ProductGroup.class );

        if (productGroup.name.isEmpty() )
            return badRequest("Name cannot be empty");

        if (Services.getProductGroupRepository().exist(clientId, productGroup.name))
            return badRequest("Category with specified name already exists");

        settings.productGroups.add(productGroup);

        Services.getClientSettingsDAO().save(settings);

        return ok( Json.toJson(productGroup) );
    }

    public static Result removeSite(String clientId, Long siteId)
    {
        Services.getProductGroupRepository().remove(siteId.intValue());

        return ok();
    }

    public static Result updateSite(String clientId, Long basicSiteId, Long resourceSiteId, String operationName )
    {
        Operation operation = Operation.valueOf(operationName);
        switch (operation)
        {
            case merge:
                Services.getGroup2ProductRepository().merge(basicSiteId.intValue(), resourceSiteId.intValue());
            case difference:
                Services.getGroup2ProductRepository().difference(basicSiteId.intValue(), resourceSiteId.intValue());
        }

        return ok();
    }

    private static enum Operation
    {
        merge, difference
    }
}
