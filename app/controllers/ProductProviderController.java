package controllers;

import com.avaje.ebean.Ebean;
import com.shopservice.ActualisationService;
import com.shopservice.MServiceInjector;
import com.shopservice.assemblers.PaginationResult;
import com.shopservice.dao.LinkedProductEntryRepository;
import com.shopservice.dao.ProductProviderRepository;
import com.shopservice.domain.ProductProvider;
import play.api.mvc.MultipartFormData;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public class ProductProviderController extends Controller {

    private static ProductProviderRepository productProviderRepository = MServiceInjector.injector.getInstance(ProductProviderRepository.class);
    private static LinkedProductEntryRepository linkedEntryRepository = MServiceInjector.injector.getInstance(LinkedProductEntryRepository.class);
    private static ActualisationService service = new ActualisationService();

    public static Result find(String clientId)
    {
        return ok(Json.toJson(productProviderRepository.find(clientId)));
    }

    public static Result findById(String clientId, String providerId)
    {
        return ok(Json.toJson(Ebean.find(ProductProvider.class, providerId) ));
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
        Object result = productProviderRepository.update(provider);

        return ok(Json.toJson(result));
    }

    public static Result autoLink(String clientId, Integer providerId)
    {
        service.autoLink(clientId, providerId);

        return ok();
    }

    public static Result remove(String clientId, Integer providerId)
    {
        productProviderRepository.remove(providerId);

        return ok();
    }

    public static Result getProducts(String clientId, Integer providerId, Boolean linked, String words, Integer offset, Integer limit )
    {
        List products = service.getNotLinkedProducts(clientId, providerId, words);
        PaginationResult result = new PaginationResult(products.size(), products.subList(offset, Math.min(products.size(), offset + limit)   ));

        return ok(Json.toJson(result));
    }

    public static Result uploadFile(String clientId, Integer providerId){
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFiles().get(0);
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();
            return ok(Json.toJson(Arrays.asList(fileName)));
        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }




}
