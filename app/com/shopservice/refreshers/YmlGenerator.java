package com.shopservice.refreshers;

import com.shopservice.MServiceInjector;
import com.shopservice.dao.HibernateProductGroupRepository;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.ProductGroup;
import com.shopservice.pricelist.models.yml.*;
import com.shopservice.pricelist.models.yml.Currency;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;

import java.util.*;

import static com.shopservice.Util.marshal;

/**
 * Created by user50 on 04.10.2014.
 */
public class YmlGenerator extends PriceListGenerator {
    private ProductGroupRepository productGroupRepository = MServiceInjector.injector.getInstance(HibernateProductGroupRepository.class);

    @Override
    public byte[] generate(String clientId, int groupId) throws Exception {
        ClientSettings clientSettings = clientSettingsRepository.get(clientId);
        ProductGroup group = productGroupRepository.get(new Long(groupId));
        DataSource dataSource = new DefaultDataSource(clientId, String.valueOf(groupId));

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
        shop.company = clientSettings.siteName;

        for (Product product : dataSource.getProducts())
            ymlCatalog.shop.offers.add(createOffer(product, group.regionalCurrency.name()));

        shop.categories = convertToYmlCategories(dataSource.getCategories());

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
