package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 10.11.13
 * Time: 11:08
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)

public class YmlCategory {
    public String id;
    public String parentId;
    public String name;
}
