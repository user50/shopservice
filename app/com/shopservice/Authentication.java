package com.shopservice;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.ClientSettings;
import play.cache.Cache;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

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
