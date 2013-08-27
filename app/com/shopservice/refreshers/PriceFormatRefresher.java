package com.shopservice.refreshers;

import com.shopservice.PriceListType;
import com.shopservice.Services;
import com.shopservice.pricelist.models.price.Item;
import com.shopservice.pricelist.models.price.Price;
import com.shopservice.queries.ItemQueryById;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import static com.shopservice.Services.clientSettings;

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
        List<String> productIds = clientSettings.getProductIds(clientId);

        Price price = new Price();
        price.setName(clientSettings.getSiteName(clientId));
        price.setUrl(clientSettings.getSiteUrl(clientId));

        for (String productId : productIds) {
            Item item = Services.getDataBaseManager(clientId).executeQueryForOne( new ItemQueryById(clientId,productId) );
            price.addItem(item);
        }

        save(price, PriceListType.price.getFileName(clientId));
    }

    public void  save (Price price, String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( Price.class );

        // marshall into XML via System.out
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1251");

        FileOutputStream outputStream = new FileOutputStream(filePath);


        marshaller.marshal( price, outputStream);

    }
}
