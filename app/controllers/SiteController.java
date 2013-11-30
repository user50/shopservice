package controllers;

import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Site;
import com.shopservice.domain.Site2Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SiteController extends Controller {

    public static Result getSites(String clientId)
    {
        return ok( Json.toJson( Services.getSiteRepository().get(clientId) ) );
    }

    public static Result addSite( String clientId )
    {
        ClientSettings settings = Services.getClientSettingsDAO().findById(clientId);
        if (settings == null)
            return badRequest("Client with id "+clientId+" doesn't exist ");

        Site site = Json.fromJson( request().body().asJson(), Site.class );

        if (site.name.isEmpty() )
            return badRequest("Name cannot be empty");

        if (Services.getSiteRepository().exist(clientId, site.name))
            return badRequest("Category with specified name already exists");

        settings.sites.add(site);

        Services.getClientSettingsDAO().save(settings);

        return ok( Json.toJson(site) );
    }

    public static Result removeSite(String clientId, Long siteId)
    {
        Services.getSiteRepository().remove(siteId.intValue());

        return ok();
    }

    public static Result updateSite(String clientId, Long basicSiteId, Long resourceSiteId, String operationName )
    {
        Operation operation = Operation.valueOf(operationName);
        switch (operation)
        {
            case merge:
                Services.getSite2ProductRepository().merge(basicSiteId.intValue(), resourceSiteId.intValue());
            case difference:
                Services.getSite2ProductRepository().difference(basicSiteId.intValue(), resourceSiteId.intValue());
        }

        return ok();
    }

    private static enum Operation
    {
        merge, difference
    }
}
