package com.shopservice.refreshers;

import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.ClientsCategoryRepository;
import com.shopservice.dao.HibernateProductGroupRepository;
import com.shopservice.domain.*;
import com.shopservice.pricelist.models.yml.*;
import com.shopservice.pricelist.models.yml.Currency;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;

import java.util.*;

import static com.shopservice.MServiceInjector.injector;
import static com.shopservice.Util.marshal;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class YMLFormatRefresher extends AbstractPriceListRefresher {
    private static ClientsCategoryRepository clientsCategoryRepository = injector.getInstance(ClientsCategoryRepository.class);

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

        if (group.useCustomCategories)
        {
            Map<String,Category> idToCategory = getCategories(clientId, query.productIds);
            for (Product product : Services.getProductDAO(clientId).find(query)) {
                ymlCatalog.shop.offers.add(createOffer(product, group.regionalCurrency.name(), idToCategory.get(product.id).id ));
                categories.add( idToCategory.get(product.id) );
            }
        }
        else
        {
            for (Product product : Services.getProductDAO(clientId).find(query)) {
                ymlCatalog.shop.offers.add(createOffer(product, group.regionalCurrency.name(), product.category.id));
                categories.add( product.category );
            }
        }

        CategorySource categorySource = group.useCustomCategories ? new CustomCategorySource():new DefaultCategorySource(clientId);

        Set<Category> relatedCategories = getCategories(categories, clientId, categorySource);

        shop.categories = convertToYmlCategories(relatedCategories);

        return marshal(ymlCatalog, clientSettings.encoding);

    }

    private Offer createOffer(Product product, String currency, String categoryId) {
        Offer offer = new Offer();
        offer.price = product.price;
        offer.currencyId = currency;
        offer.id = product.id + product.category.id;
        offer.categoryId = categoryId;
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

    private Map<String,Category> getCategories(String clientId, Collection<String> productIds)
    {
        Map<String, ClientsCategory> productIdToClientsCategory =
                clientsCategoryRepository.getByProductIds(clientId, productIds);

        Map<String, Category> result = new HashMap<>();

        for (Map.Entry<String, ClientsCategory> entry : productIdToClientsCategory.entrySet()) {
            String productId = entry.getKey();

            Category category = new Category(entry.getValue().id.toString());
            category.name = entry.getValue().name;

            if (entry.getValue().parentId != null)
                category.parentId = entry.getValue().parentId.toString();

            result.put(productId, category);
        }

        return result;
    }


}
