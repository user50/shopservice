package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 10.11.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
@XmlType(propOrder = {"url", "price", "currencyId", "categoryId", "picture",
                      "delivery", "local_delivery_cost", "name", "vendor",
                      "vendorCode", "description", "country_of_origin", "adult"})
@XmlAccessorType(XmlAccessType.FIELD)

public class Offer {
    @XmlAttribute
    public String id;

    @XmlAttribute
    public String type;

    @XmlAttribute
    public Boolean available;

    public String url;
    public Double price;
    public String currencyId;
    public String name;
    public String categoryId;
    public String picture;
    public Boolean delivery;
    public String local_delivery_cost;
    public String vendor;
    public String vendorCode;
    public String description;
    public String country_of_origin;
    public String adult;
//    public List<Parameter> param;
}
