package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 10.11.13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)

public class Currency {
    @XmlAttribute
    public String id;

    @XmlAttribute
    public String rate;
}
