package com.shopservice;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;
import play.cache.Cache;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 12.10.13
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class Authentication extends Action.Simple {
    @Override
    public Result call(Http.Context context) throws Throwable {
        Http.Cookie cookie = context.request().cookie("key");

        if (cookie == null)
            return redirect("/assets/login.html");

        if (Cache.get(cookie.value()) == null)
            return redirect("/assets/login.html");

        if (Ebean.find(ClientSettings.class, (String) Cache.get(cookie.value())) == null)
            return redirect("/assets/login.html");

        return delegate.call(context);
    }
}
