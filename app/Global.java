import com.shopservice.MailService;
import play.GlobalSettings;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class Global extends GlobalSettings {
    @Override
    public Result onError(Http.RequestHeader requestHeader, Throwable throwable) {
        Throwable exception = throwable.getCause();
        MailService.getInstance().report(exception);

        return Results.status(500);
    }


}
