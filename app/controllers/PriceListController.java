package controllers;

import com.shopservice.MServiceInjector;
import com.shopservice.PriceListType;
import com.shopservice.dao.ClientSettingsRepository;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import static com.shopservice.MServiceInjector.injector;

public class PriceListController extends Controller {

    private static ProductGroupRepository productGroupRepository = MServiceInjector.injector.getInstance(ProductGroupRepository.class);
    private static ClientSettingsRepository clientSettingsRepository = injector.getInstance(ClientSettingsRepository.class);

    public static Result generatePriceList(String clientId, Long siteId, String format) throws Exception {

        byte[] priceList = PriceListType.valueOf(format).getHandler().generate(clientId, siteId.intValue());

        ClientSettings clientSettings = clientSettingsRepository.findById(clientId);

        response().setHeader("Content-Disposition", "attachment; filename=\""+clientSettings.siteName+"-"+ productGroupRepository.getName(siteId.intValue())+".xml\"" );
        return ok(priceList).as("application/force-download");
    }

    public static Result getPriceListFormats()
    {
        return ok(Json.toJson(PriceListType.values()));
    }

}
