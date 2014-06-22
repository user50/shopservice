package com.shopservice.refreshers;

import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.domain.Category;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Product;
import com.shopservice.pricelist.models.yml.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shopservice.Util.marshal;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class YMLFormatRefresher extends AbstractPriceListRefresher {
    @Override
    public byte[] generate(String clientId, int siteId) throws Exception {
        ClientSettings clientSettings = clientSettingsRepository.findById(clientId);

        YmlCatalog ymlCatalog = new YmlCatalog();
        Shop shop = new Shop();
        ymlCatalog.shop = shop;

        Currency currency = new Currency();
        currency.id = clientSettings.currency.name();
        currency.rate = clientSettings.currency.getRate();
        shop.currencies.add(currency);

        shop.name = clientSettings.siteName;
        shop.url = clientSettings.siteUrl;

        Set<Category> categories = new HashSet<Category>();

        ProductConditions query = new ProductConditions();
        query.productIds = getProductIds(clientId, siteId);

        for (Product product : Services.getProductDAO(clientId).find( query )) {
            ymlCatalog.shop.offers.add(createOffer(product, clientSettings.currency.name()));
            categories.add( product.category );
        }

        Set<Category> relatedCategories = getCategories(categories, clientId);

        shop.categories = convertToYmlCategories(relatedCategories);

        return marshal(ymlCatalog, clientSettings.encoding);

    }

    private Offer createOffer(Product product, String currency) {
        Offer offer = new Offer();
        offer.price = product.price;
        offer.currencyId = currency;
        offer.id = product.id + product.category.id;
        offer.categoryId = product.category.id;
        offer.description = product.description;
        offer.vendor = product.manufacturer;
        offer.name = product.name;
        offer.picture = product.imageUrl;
        offer.url = product.url;
        offer.available = product.available;

        return  offer;
    }

    private List<YmlCategory> convertToYmlCategories(Set<Category> relatedCategories) {
        List<YmlCategory> result = new ArrayList<YmlCategory>();

        for (Category relatedCategory : relatedCategories) {
            YmlCategory ymlCategory = new YmlCategory();
            ymlCategory.id = relatedCategory.id;
            ymlCategory.name = relatedCategory.name;
            ymlCategory.parentId = relatedCategory.parentId;
            result.add(ymlCategory);
        }

        return result;
    }


}
