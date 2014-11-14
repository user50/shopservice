package com.shopservice.pricelist.models.price;

import javax.xml.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 09.11.12
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "price")
public class Price {
    @XmlElement
    private String date;

    private String firmName;

    private String url;

    private String rate;

    @XmlElementWrapper(name="categories")
    @XmlElements({
            @XmlElement(name="category") }
    )
    private List<Category> catalog;

    @XmlElementWrapper(name="items")
    @XmlElements({
            @XmlElement(name="item") }
    )
    private List<Item> items;

    public Price() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        date = dateFormat.format(new Date());
    }

    public String getDate() {
        return date;
    }

    public String getFirmName() {
        return firmName;
    }

    public String getUrl() {
        return url;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void addCategory(Category category){

        if (catalog==null)
            catalog = new ArrayList<Category>();

        catalog.add(category);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem (Item item){

        if (items==null)
            items = new ArrayList<Item>();

        items.add(item);
    }

    public void setCatalog(List<Category> catalog) {
        this.catalog = catalog;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
