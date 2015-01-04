package controllers;

import com.shopservice.Services;
import com.shopservice.sync.DefaultSyncProducts;
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

    public static Result sync(String clientId){
        Services.getSyncProduct(clientId).execute();
        return ok();
    }
}
