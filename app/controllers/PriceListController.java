package controllers;

import com.shopservice.PriceListType;
import com.shopservice.Services;
import com.shopservice.domain.ClientSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static com.shopservice.Services.priceListService;

public class PriceListController extends Controller {

    public static Result getPriceList(String clientId, String format)
    {
        File file = priceListService.getPriceList( clientId, PriceListType.valueOf(format) );

        ClientSettings clientSettings = ClientSettings.findById(clientId);

        response().setHeader("Content-Disposition", "attachment; filename=\""+clientSettings.siteName+".xml\"" );
        return ok(file).as("application/force-download");
    }

    public static Result refreshPriceList(String clientId, String format) throws JAXBException, SQLException, FileNotFoundException {
        priceListService.refreshPriceList( clientId, PriceListType.valueOf(format) );

        return ok();
    }

    public static Result getPriceListFormats(String clientId)
    {
        return ok(Json.toJson(PriceListType.values()));
    }

}
