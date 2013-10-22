package com.shopservice.refreshers;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public interface PriceListRefresher {
    public void refresh(String clientId, int siteId) throws SQLException, JAXBException, FileNotFoundException;
}
