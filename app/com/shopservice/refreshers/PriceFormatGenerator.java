package com.shopservice.refreshers;

import com.shopservice.MServiceInjector;
import com.shopservice.ProductConditions;
import com.shopservice.Services;
import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ClientSettings;
import com.shopservice.transfer.Product;
import com.shopservice.domain.ProductGroup;
import com.shopservice.pricelist.models.price.Category;
import com.shopservice.pricelist.models.price.Item;
import com.shopservice.pricelist.models.price.Price;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.shopservice.Util.marshal;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class PriceFormatGenerator extends PriceListGenerator {


    @Override
    public byte[] generate(String clientId, int groupId) throws Exception {
        ClientSettings clientSettings = clientSettingsRepository.get(clientId);
        ProductGroup group = MServiceInjector.injector.getInstance(ProductGroupRepository.class).get(new Long(groupId));

        Price price = new Price();
        price.setFirmName(clientSettings.siteName) ;
        price.setUrl( clientSettings.siteUrl );
        price.setRate(group.rate == null ? null : group.rate);

        Set<Category> categories = new HashSet<Category>();

        ProductConditions query = new ProductConditions();
        query.productIds = getProductIds(clientId, groupId, false);

        for (Product product : Services.getProductDAO(clientId).find(query)) {
            price.addItem( createItem(clientId, product) );
            categories.add( new Category( product.category.id, product.category.name) );
        }

        price.setCatalog( new ArrayList<Category>(categories) );

        return marshal(price, clientSettings.encoding);
    }

    private Item createItem(String clientId, Product product) throws SQLException {
        return new Item(product.id, product.name, product.url, product.price,
                product.category.id, product.manufacturer, product.imageUrl, product.description );
    }
}
