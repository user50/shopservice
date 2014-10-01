package controllers;

import com.shopservice.MServiceInjector;
import com.shopservice.Validator;
import com.shopservice.dao.ClientsCategoryRepository;
import com.shopservice.domain.ClientsCategory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ClientsCategoryController extends Controller{
    private static ClientsCategoryRepository clientsCategoryRepository = MServiceInjector.injector.getInstance(ClientsCategoryRepository.class);

    public static Result get(String clientId){
        return ok(Json.toJson(clientsCategoryRepository.get(clientId)));
    }

    public static Result create(String clientId){
        ClientsCategory clientsCategory = Json.fromJson(request().body().asJson(), ClientsCategory.class);
        clientsCategoryRepository.add(clientId, clientsCategory);

        return ok(Json.toJson(clientsCategory));
    }

    public static Result update(String clientId, String clientsCategoryId){
        ClientsCategory clientsCategory = Json.fromJson(request().body().asJson(), ClientsCategory.class);
        clientsCategoryRepository.update(clientId, clientsCategory);

        return ok(Json.toJson(clientsCategory));
    }

    public static Result delete(String clientId, Integer clientCategoryId){
        Validator.validateDelete(clientCategoryId);
        clientsCategoryRepository.delete(clientCategoryId);

        return ok(Json.toJson(clientCategoryId));
    }

}
