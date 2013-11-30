package controllers;

import com.shopservice.PriceListType;
import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Site;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static com.shopservice.Services.priceListService;

public class PriceListController extends Controller {

    public static Result getPriceList(String clientId, Long siteId, String format)
    {
        File file = priceListService.getPriceList( clientId, siteId.intValue(), PriceListType.valueOf(format) );

        if (file == null)
            return badRequest("The file does not exist yet. You need to generate it at least once");

        ClientSettings clientSettings = Services.getClientSettingsDAO().findById(clientId);

        response().setHeader("Content-Disposition", "attachment; filename=\""+clientSettings.siteName+"-"+ Site.getName(siteId.intValue())+".xml\"" );
        return ok(file).as("application/force-download");
    }

    public static Result refreshPriceList(String clientId, Long siteId,  String format) throws Exception {
        priceListService.refreshPriceList( clientId, siteId.intValue(), PriceListType.valueOf(format) );

        return ok();
    }

    public static Result getPriceListFormats(String clientId)
    {
        return ok(Json.toJson(PriceListType.values()));
    }

}
