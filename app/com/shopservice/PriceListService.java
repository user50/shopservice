package com.shopservice;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 12:51
 * To change this template use File | Settings | File Templates.
 */
public class PriceListService {

    public File getPriceList(String clientId, int siteId, PriceListType type){
        File file = new File(type.getFileName(clientId, siteId));

        return file.exists() ? file:null;
    }

    public void refreshPriceList(String clientId, int siteId , PriceListType type) throws SQLException, JAXBException, FileNotFoundException {
        type.getHandler().refresh(clientId, siteId);
    }
}
