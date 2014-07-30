package com.shopservice;

import com.shopservice.dao.ClientSettingsRepository;
import play.cache.Cache;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;

import static com.shopservice.MServiceInjector.injector;

public class Authentication extends Action.Simple {

    private ClientSettingsRepository repository = injector.getInstance(ClientSettingsRepository.class);

    @Override
    public F.Promise<SimpleResult> call(final Http.Context context) throws Throwable {
        final Http.Cookie cookie = context.request().cookie("key");

        if (cookie == null || Cache.get(cookie.value()) == null || repository.findById((String) Cache.get(cookie.value())) == null )
            return F.Promise.promise( new F.Function0<play.mvc.SimpleResult>()
            {
                @Override
                public play.mvc.SimpleResult apply() throws Throwable {
                    return redirect("/assets/login.html");
                }
            });

        return delegate.call(context);
    }
}
