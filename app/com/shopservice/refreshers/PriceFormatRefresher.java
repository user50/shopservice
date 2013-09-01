package com.shopservice.refreshers;

import com.shopservice.PriceListType;
import com.shopservice.Services;
import com.shopservice.Util;
import com.shopservice.domain.Product;
import com.shopservice.pricelist.models.price.Catalog;
import com.shopservice.pricelist.models.price.Item;
import com.shopservice.pricelist.models.price.Price;
import com.shopservice.queries.ProductQueryById;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static com.shopservice.Services.CLIENT_SETTINGS_SERVICE;
import static com.shopservice.Util.save;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class PriceFormatRefresher implements PriceListRefresher {
    @Override
    public void refresh(String clientId) throws SQLException, JAXBException, FileNotFoundException {
        List<String> productIds = CLIENT_SETTINGS_SERVICE.getProductIds(clientId);

        Catalog catalog = new Catalog();
        Price price = new Price();
        price.setName( CLIENT_SETTINGS_SERVICE.getClientSettings(clientId).siteName ) ;
        price.setUrl( CLIENT_SETTINGS_SERVICE.getClientSettings(clientId).siteUrl );

        for (String productId : productIds) {
            Product product = Services.getDataBaseManager(clientId).executeQueryForOne( new ProductQueryById( clientId,productId ) );
            price.addItem( createItem(clientId, product, catalog) );
        }

        price.setCatalog(catalog.getCategories());

        save(price, PriceListType.price.getFileName(clientId));
    }

    private Item createItem(String clientId, Product product, Catalog catalog) throws SQLException {
        Util.modifyUrl(clientId, product);
        Util.modifyImageUrl(clientId, product);

        String categoryId = catalog.getManufacturerId(product.categoryName, product.manufacturer);

        return new Item(product.id, product.name, product.url, product.price, categoryId, product.manufacturer, product.imageUrl, product.description );
    }


}
