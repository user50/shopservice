package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 10.11.13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)

public class Shop {

    public String name;
    public String company;
    public String url;
    public String platform;
    public String version;
    public String agency;
    public String email;

    @XmlElementWrapper(name="currencies")
    @XmlElements({@XmlElement(name="currency") })
    public List<Currency> currencies = new ArrayList<>();

    @XmlElementWrapper(name="categories")
    @XmlElements({@XmlElement(name="category") })
    public List<YmlCategory> categories = new ArrayList<>();

    @XmlElementWrapper(name="offers")
    @XmlElements({@XmlElement(name="offer") })
    public List<Offer> offers = new ArrayList<>();


}
