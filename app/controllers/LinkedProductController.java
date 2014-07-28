package controllers;

import com.shopservice.MServiceInjector;
import com.shopservice.assemblers.LinkedProductAssembler;
import com.shopservice.dao.LinkedProductEntryRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.dao.ProductProviderRepository;
import com.shopservice.domain.LinkedProductEntry;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by user50 on 13.07.2014.
 */
public class LinkedProductController extends Controller {

    private static LinkedProductEntryRepository linkedEntryRepository = MServiceInjector.injector.getInstance(LinkedProductEntryRepository.class);
    private static ProductProviderRepository productProviderRepository = MServiceInjector.injector.getInstance(ProductProviderRepository.class);
    private static ProductEntryRepository productEntryRepository = MServiceInjector.injector.getInstance(ProductEntryRepository.class);
    private static LinkedProductAssembler assembler = LinkedProductAssembler.getInstance();

    public static Result find(String clientId, Integer providerId)
    {
        return ok(Json.toJson(assembler.find(clientId, providerId)));
    }

    public static Result create(String clientId, Integer providerId)
    {
        LinkedProductEntry linkedEntry = Json.fromJson(request().body().asJson(), LinkedProductEntry.class );

        linkedEntry.productProvider = productProviderRepository.find(providerId);
        linkedEntry.productEntry = productEntryRepository.find(clientId, linkedEntry.clientProductId);
        Object result = linkedEntryRepository.create(linkedEntry);

        return ok(Json.toJson(result));
    }

    public static Result remove(String clientId, Integer providerId, Integer linkedEntryId)
    {
        linkedEntryRepository.remove(linkedEntryId);

        return ok();
    }

}
