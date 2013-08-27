package com.shopservice;

import com.shopservice.refreshers.PriceFormatRefresher;
import com.shopservice.refreshers.PriceListRefresher;
import com.shopservice.refreshers.YMLFormatRefresher;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public enum PriceListType {
    price("price.xml", new PriceFormatRefresher()), YML("yml.xml", new YMLFormatRefresher() );

    private static final String PRICE_LIST_DIR = "pricelists";

    private String fileName;
    private PriceListRefresher refresher;

    private PriceListType(String fileName, PriceListRefresher refresher) {
        this.refresher = refresher;
        this.fileName = fileName;
    }

    public String getFileName(String clientId) {
        return  System.getProperty("user.dir") + File.separator + PRICE_LIST_DIR +
                File.separator + clientId + File.separator+fileName;
    }

    public PriceListRefresher getHandler()
    {
        return refresher;
    }
}
