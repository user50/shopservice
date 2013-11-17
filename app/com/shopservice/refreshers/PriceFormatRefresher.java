package com.shopservice.refreshers;

import com.shopservice.PriceListType;
import com.shopservice.Services;
import com.shopservice.Util;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;
import com.shopservice.pricelist.models.price.Catalog;
import com.shopservice.pricelist.models.price.Category;
import com.shopservice.pricelist.models.price.Item;
import com.shopservice.pricelist.models.price.Price;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shopservice.Util.save;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class PriceFormatRefresher extends AbstractPriceListRefresher {
    @Override
    public void refresh(String clientId, int siteId) throws SQLException, JAXBException, FileNotFoundException {
        ClientSettings clientSettings = ClientSettings.findById(clientId);

        Price price = new Price();
        price.setName( clientSettings.siteName ) ;
        price.setUrl( clientSettings.siteUrl );

        Set<Category> categories = new HashSet<Category>();

        for (Product product : Services.getProductDAO(clientId).getProducts( getProductIds(clientId, siteId))) {
            price.addItem( createItem(clientId, product) );
            categories.add( new Category( product.category.id, product.category.name) );
        }

        price.setCatalog( new ArrayList<Category>(categories) );

        save(price, PriceListType.price.getFileName(clientId, siteId));
    }

    private Item createItem(String clientId, Product product) throws SQLException {
        return new Item(product.id, product.name, product.url, product.price,
                product.category.id, product.manufacturer, product.imageUrl, product.description );
    }
}
