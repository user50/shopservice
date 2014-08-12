package com.shopservice.dao;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.shopservice.LinkedEntryCondition;
import com.shopservice.domain.LinkedProductEntry;

import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public class EbeanLinkedProductEntryRepository implements LinkedProductEntryRepository {

    @Override
    public List<LinkedProductEntry> find(LinkedEntryCondition condition) {
        ExpressionList<LinkedProductEntry> expression = Ebean.find(LinkedProductEntry.class).fetch("productEntry").where();

        if (condition.providerId != null)
            expression = expression.eq("product_provider_id", condition.providerId);

        if (condition.linked != null)
            expression = condition.linked ? expression.isNotNull("product_id") : expression.isNull("product_id");

//        if (condition.offset != null && condition.limit != null)
//            return expression.setMaxRows(condition.limit).setFirstRow(condition.offset).findList();

        return expression.findList();
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
