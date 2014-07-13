package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.shopservice.domain.LinkedProductEntry;

import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public class EbeanLinkedProductEntryRepository implements LinkedProductEntryRepository {

    @Override
    public List<LinkedProductEntry> find(Integer providerId) {
        return Ebean.find(LinkedProductEntry.class).fetch("productEntry").where().eq("product_provider_id", providerId).findList();
    }

    @Override
    public LinkedProductEntry create(LinkedProductEntry linkedProductEntry) {
        Ebean.save(linkedProductEntry);

        return linkedProductEntry;
    }

    @Override
    public LinkedProductEntry update(LinkedProductEntry linkedProductEntry) {
        Ebean.update(linkedProductEntry);

        return linkedProductEntry;
    }

    @Override
    public void remove(Integer linkedProductEntryId) {
        Ebean.delete(LinkedProductEntry.class, linkedProductEntryId );
    }
}
