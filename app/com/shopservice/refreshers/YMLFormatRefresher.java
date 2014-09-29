package com.shopservice.refreshers;

import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.HibernateProductGroupRepository;
import com.shopservice.domain.*;
import com.shopservice.pricelist.models.yml.*;
import com.shopservice.pricelist.models.yml.Currency;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;

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
    public byte[] generate(String clientId, int groupId) throws Exception {
        ClientSettings clientSettings = clientSettingsRepository.get(clientId);
        ProductGroup group = MServiceInjector.injector.getInstance(HibernateProductGroupRepository.class).get(new Long(groupId));

        YmlCatalog ymlCatalog = new YmlCatalog();
        Shop shop = new Shop();
        ymlCatalog.shop = shop;

        Currency regionalCurrency = new Currency(group.regionalCurrency.name() );
        shop.currencies.add(regionalCurrency);

        if (group.productCurrency != null && group.rate != null && !group.productCurrency.equals(group.regionalCurrency))
        {
            Currency productCurrency = new Currency(group.productCurrency.name(), group.rate);
            shop.currencies.add(productCurrency);
        }

        shop.name = clientSettings.siteName;
        shop.url = clientSettings.siteUrl;

        Set<Category> categories = new HashSet<Category>();

        ProductConditions query = new ProductConditions();
        query.productIds = getProductIds(clientId, groupId, group.useCustomCategories);

        for (Product product : Services.getProductDAO(clientId).find(query)) {
            ymlCatalog.shop.offers.add(createOffer(product, group.regionalCurrency.name()));
            categories.add( product.category );
        }

        CategorySource categorySource = group.useCustomCategories ? new CustomCategorySource():new DefaultCategorySource(clientId);

        Set<Category> relatedCategories = getCategories(categories, clientId, categorySource);

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
