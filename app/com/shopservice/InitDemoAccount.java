package com.shopservice;

import com.shopservice.dao.ClientSettingsRepository;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;

import static com.shopservice.MServiceInjector.injector;

/**
 * Created by Yevhen on 03.08.14.
 */
public class InitDemoAccount extends Action.Simple {

    private ClientSettingsRepository repository = injector.getInstance(ClientSettingsRepository.class);

    @Override
    public F.Promise<SimpleResult> call(final Http.Context context) throws Throwable {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if(repository.get("demo") != null)
                        Services.getCategoryDAO("demo").getCategories();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return delegate.call(context);
    }
}
