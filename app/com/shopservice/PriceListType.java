package com.shopservice;

import com.shopservice.refreshers.PriceFormatGenerator;
import com.shopservice.refreshers.PriceListRefresher;
import com.shopservice.refreshers.YmlGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public enum PriceListType {
    price(new PriceFormatGenerator()), YML(new YmlGenerator() );

    private PriceListRefresher refresher;

    private PriceListType(PriceListRefresher refresher) {
        this.refresher = refresher;
    }

    public PriceListRefresher getHandler()
    {
        return refresher;
    }
}
