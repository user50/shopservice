package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 16.11.13
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="yml_catalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class YmlCatalog {

    @XmlAttribute
    public String date;
    public Shop shop;

    public YmlCatalog() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        date = dateFormat.format(new Date());
    }
}
