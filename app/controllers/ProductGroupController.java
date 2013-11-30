package controllers;

import com.shopservice.MServiceInjector;
import com.shopservice.Services;
import com.shopservice.dao.Group2ProductRepository;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import static com.shopservice.MServiceInjector.injector;

public class ProductGroupController extends Controller {

    private static ProductGroupRepository productGroupRepository = injector.getInstance(ProductGroupRepository.class);
    private static Group2ProductRepository group2ProductRepository = injector.getInstance(Group2ProductRepository.class);


    public static Result getSites(String clientId)
    {
        return ok( Json.toJson( productGroupRepository.get(clientId) ) );
    }

    public static Result addSite( String clientId )
    {
        ClientSettings settings = Services.getClientSettingsDAO().findById(clientId);
        if (settings == null)
            return badRequest("Client with id "+clientId+" doesn't exist ");

        ProductGroup productGroup = Json.fromJson( request().body().asJson(), ProductGroup.class );

        if (productGroup.name.isEmpty() )
            return badRequest("Name cannot be empty");

        if (productGroupRepository.exist(clientId, productGroup.name))
            return badRequest("Category with specified name already exists");

        settings.productGroups.add(productGroup);

        Services.getClientSettingsDAO().save(settings);

        return ok( Json.toJson(productGroup) );
    }

    public static Result removeSite(String clientId, Long siteId)
    {
        productGroupRepository.remove(siteId.intValue());

        return ok();
    }

    public static Result updateSite(String clientId, Long basicSiteId, Long resourceSiteId, String operationName )
    {
        Operation operation = Operation.valueOf(operationName);
        switch (operation)
        {
            case merge:
                group2ProductRepository.merge(basicSiteId.intValue(), resourceSiteId.intValue());
            case difference:
                group2ProductRepository.difference(basicSiteId.intValue(), resourceSiteId.intValue());
        }

        return ok();
    }

    private static enum Operation
    {
        merge, difference
    }
}
