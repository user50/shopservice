package com.shopservice;

import com.avaje.ebean.Ebean;
import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.domain.ClientSettings;
import play.cache.Cache;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.SimpleResult;

import static com.shopservice.MServiceInjector.injector;
import static play.libs.F.Promise;

public class Authentication extends Action.Simple {

    private ClientSettingsRepository repository = injector.getInstance(ClientSettingsRepository.class);

    @Override
    public F.Promise<SimpleResult> call(Http.Context context) throws Throwable {
        Http.Cookie cookie = context.request().cookie("key");

        if (cookie == null)
            return Promise.pure(redirect("/assets/login.html"));

        if (Cache.get(cookie.value()) == null)
            return Promise.pure(redirect("/assets/login.html"));

        if (repository.findById( (String) Cache.get(cookie.value()) ) == null)
            return Promise.pure(redirect("/assets/login.html"));

        return delegate.call(context);
    }
}
