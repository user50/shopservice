package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 10.11.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Offer {
    @XmlAttribute
    public String id;

    @XmlAttribute
    public String type;

    @XmlAttribute
    public Boolean available;

    @XmlAttribute
    public String bid;

    public String url;
    public Double price;
    public String currencyId;
    public String name;
    public String categoryId;
    public String picture;
    public Boolean store;
    public Boolean pickup;
    public Boolean delivery;
    public String local_delivery_cost;
    public String typePrefix;
    public String vendor;
    public String vendorCode;
    public String model;
    public String description;
    public String sales_notes;
    public Boolean manufacturer_warranty;
    public String seller_warranty;
    public String country_of_origin;
    public String barcode;
    public String cpa;
    public List<Parameter> param;
    public String rec;
    public String expiry;
    public String weight;
    public String dimensions;




}
