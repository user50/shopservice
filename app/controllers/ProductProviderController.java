package controllers;

import com.shopservice.MServiceInjector;
import com.shopservice.dao.ProductProviderRepository;
import com.shopservice.domain.ProductProvider;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by user50 on 13.07.2014.
 */
public class ProductProviderController extends Controller {

    private static ProductProviderRepository productProviderRepository = MServiceInjector.injector.getInstance(ProductProviderRepository.class);

    public static Result find(String clientId)
    {
        return ok(Json.toJson(productProviderRepository.find(clientId)));
    }

    public static Result create(String clientId)
    {
        ProductProvider provider = Json.fromJson(request().body().asJson(), ProductProvider.class );

        Object result = productProviderRepository.create(clientId, provider);

        return ok(Json.toJson(result));
    }

    public static Result update(String clientId, Integer providerId)
    {
        ProductProvider provider = Json.fromJson(request().body().asJson(), ProductProvider.class );
        Object result = productProviderRepository.update( provider);

        return ok(Json.toJson(result));
    }

    public static Result remove(String clientId, Integer providerId)
    {
        productProviderRepository.remove(providerId);

        return ok();
    }
}
