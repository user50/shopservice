package com.shopservice.refreshers;

import com.shopservice.PriceListType;
import com.shopservice.Services;
import com.shopservice.domain.CategoryEntry;
import com.shopservice.domain.ClientSettings;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;
import com.shopservice.pricelist.models.price.Catalog;
import com.shopservice.pricelist.models.price.Item;
import com.shopservice.pricelist.models.price.Price;
import com.shopservice.queries.ProductQueryByCategories;
import com.shopservice.queries.ProductQueryById;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;
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
public class PriceFormatRefresher implements PriceListRefresher {
    @Override
    public void refresh(String clientId) throws SQLException, JAXBException, FileNotFoundException {
        List<ProductEntry> entries = ProductEntry.find(clientId);
        ClientSettings clientSettings = ClientSettings.findById(clientId);

        Catalog catalog = new Catalog();
        Price price = new Price();
        price.setName( clientSettings.siteName ) ;
        price.setUrl( clientSettings.siteUrl );

        for (String productId : getProductIds(clientId)) {
            Product product = Services.getDataBaseManager(clientId).executeQueryForOne( new ProductQueryById( clientId, productId ) );
            price.addItem( createItem(product, catalog) );
        }

        price.setCatalog(catalog.getCategories());

        save(price, PriceListType.price.getFileName(clientId));
    }

    private Set<String> getProductIds(String clientId) throws SQLException {
        Set<String> setOfProductIds = new HashSet<String>();

        for (ProductEntry productEntry : ProductEntry.find(clientId))
            setOfProductIds.add(productEntry.productId);

        Set<String> setOfCategoryIds = new HashSet<String>();
        for (CategoryEntry categoryEntry : CategoryEntry.find(clientId))
            setOfCategoryIds.add( categoryEntry.categoryId );

        for (Product product : Services.getDataBaseManager(clientId).executeQueryForList(new ProductQueryByCategories(clientId, setOfCategoryIds)))
            setOfProductIds.add(product.id);

        return setOfCategoryIds;
    }

    private Item createItem(Product product, Catalog catalog) {
        if (product.url == null);
        //todo get url ;

        if (product.imageUrl == null);
        //todo get imageUrl  ;

        String categoryId = catalog.getManufacturerId(product.categoryName, product.manufacturer);

        return new Item(product.id, product.name, product.url, product.price, categoryId, product.manufacturer, product.imageUrl, product.description );
    }


}
