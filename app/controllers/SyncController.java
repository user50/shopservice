package controllers;

import com.shopservice.Services;
import com.shopservice.dao.SynchronizeProducts;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 29.12.14
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class SyncController extends Controller {

    private static SynchronizeProducts synchronizeProducts = new SynchronizeProducts();

    public static Result sync(String clientId){

        synchronizeProducts.syncProducts(Services.getProductDAO(clientId), clientId);

        return ok();
    }
}
