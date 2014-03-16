package com.shopservice.pricelist.models.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 16.11.13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)

public class Parameter {
    @XmlAttribute
    public String name;

    @XmlAttribute
    public String value;

    public Parameter(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public Parameter() {
    }
}
