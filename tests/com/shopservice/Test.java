package com.shopservice;

import com.google.common.collect.Sets;
import com.shopservice.pricelist.models.yml.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 16.11.13
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    @org.junit.Test
    public void testName() throws Exception {
        YmlCatalog ymlCatalog = new YmlCatalog();
        Shop shop = new Shop();
        ymlCatalog.shop = shop;

        shop.name = "Dom";
        Currency currency = new Currency();
        currency.id = "UAH";
        currency.rate = "1";

        shop.currencies.add(currency);

        Offer offer1 = new Offer();
        offer1.price = 21.2;
        offer1.country_of_origin = "USA";
        offer1.param = new ArrayList<>();
        offer1.param.add(new Parameter("color", "red"));
        offer1.param.add(new Parameter("weight", "100"));

        shop.offers.add(offer1);

        Util.save(ymlCatalog, "c:\\projects\\shopservice\\dom.xml");

    }

    private Set<YmlCategory> getParents(Set<YmlCategory> children)
    {
        //TODO
        return null;
    }

    private Set<YmlCategory> getRelatedCategories(Set<YmlCategory> categories)
    {
        Set<YmlCategory> parents = getParents(categories);

        if (parents.isEmpty())
            return parents;
        else
            return Sets.union(parents, getRelatedCategories(parents) );
    }

    private Set<YmlCategory> getCategories(Set<YmlCategory> categories)
    {
        return Sets.union(categories, getRelatedCategories(categories));

    }
}
