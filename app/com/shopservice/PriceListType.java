package com.shopservice;

import com.shopservice.refreshers.PriceFormatRefresher;
import com.shopservice.refreshers.PriceListRefresher;
import com.shopservice.refreshers.YMLFormatRefresher;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public enum PriceListType {
    price(new PriceFormatRefresher()), YML(new YMLFormatRefresher() );

    private PriceListRefresher refresher;

    private PriceListType(PriceListRefresher refresher) {
        this.refresher = refresher;
    }

    public PriceListRefresher getHandler()
    {
        return refresher;
    }
}
