import com.shopservice.HibernateUtil;
import com.shopservice.MailService;
import com.shopservice.exception.ValidationException;
import play.Application;
import play.GlobalSettings;
import play.Play;
import play.api.mvc.RequestHeader;
import play.libs.F;
import play.libs.F.*;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.SimpleResult;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {
    @Override
    public Promise<SimpleResult> onError(Http.RequestHeader requestHeader, Throwable throwable) {

        Throwable exception = throwable.getCause();

        if (exception instanceof ValidationException) {
            ValidationException validationException = (ValidationException) exception;
            return Promise.<SimpleResult>pure(status(422, Json.toJson(validationException.getDescription())));
        }


        if ( Play.application().configuration().getBoolean("errorReporting") )
            MailService.getInstance().report(exception);

        return super.onError(requestHeader, throwable);
    }

    @Override
    public void onStop(Application application) {
        HibernateUtil.stop();
    }
}
