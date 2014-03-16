import com.shopservice.MailService;
import play.GlobalSettings;
import play.api.libs.concurrent.Promise;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.SimpleResult;
import scala.concurrent.impl.Future;

public class Global extends GlobalSettings {
    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader requestHeader, Throwable throwable) {
        Throwable exception = throwable.getCause();
        MailService.getInstance().report(exception);

        Promise.apply().success(Results.status(500));

        return super.onError(requestHeader, throwable);
    }


}
