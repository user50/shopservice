package controllers;

import com.shopservice.Validator;
import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.dao.Group2ProductRepository;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

import static com.shopservice.MServiceInjector.injector;

public class ProductGroupController extends Controller {

    private static ProductGroupRepository productGroupRepository = injector.getInstance(ProductGroupRepository.class);
    private static Group2ProductRepository group2ProductRepository = injector.getInstance(Group2ProductRepository.class);
    private static ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    public static Result get(String clientId)
    {
        return ok( Json.toJson( productGroupRepository.get(clientId) ) );
    }

    public static Result add( String clientId )
    {
        ClientSettings settings = clientSettingsRepository.get(clientId);
        if (settings == null)
            return badRequest("Client with id "+clientId+" doesn't exist ");

        ProductGroup productGroup = Json.fromJson( request().body().asJson(), ProductGroup.class );

        Validator.validate(productGroup);
        productGroup.clientSettings = settings;
        productGroupRepository.save( productGroup );

        return ok( Json.toJson(productGroup) );
    }

    public static Result remove(String clientId, Long siteId)
    {
        productGroupRepository.remove(siteId.intValue());

        return ok();
    }

    public static Result update(String clientId, Long groupId)
    {
        ProductGroup group = productGroupRepository.get( groupId);

        if (group == null)
            return notFound();

        ProductGroup fromRequest = Json.fromJson(request().body().asJson(), ProductGroup.class);

        group.name = fromRequest.name;
        group.format = fromRequest.format;
        group.regionalCurrency = fromRequest.regionalCurrency;
        group.productCurrency = fromRequest.productCurrency;
        group.rate = fromRequest.rate;

        Validator.validate(group);
        productGroupRepository.update(group);

        return ok(Json.toJson(group));
    }

    public static Result merge(String clientId, Long groupId)
    {
        Map<String,String> body = Json.fromJson(request().body().asJson(), Map.class);
        if (!body.containsKey("resourceGroupId"))
            return badRequest("Missing field resourceGroupId");

        group2ProductRepository.merge(groupId.intValue(), Integer.parseInt(body.get("resourceGroupId")));

        return ok();
    }

    public static Result diff(String clientId, Long groupId)
    {
        Map<String,String> body = Json.fromJson(request().body().asJson(), Map.class);
        if (!body.containsKey("resourceGroupId"))
            return badRequest("Missing field resourceGroupId");

        group2ProductRepository.difference(groupId.intValue(), Integer.parseInt(body.get("resourceGroupId")));

        return ok();
    }
}
