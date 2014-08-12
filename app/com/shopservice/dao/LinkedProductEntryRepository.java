package com.shopservice.dao;

import com.shopservice.LinkedEntryCondition;
import com.shopservice.domain.LinkedProductEntry;

import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public interface LinkedProductEntryRepository {

    public List<LinkedProductEntry> find(LinkedEntryCondition condition);

    public LinkedProductEntry create(LinkedProductEntry linkedProductEntry);

    public LinkedProductEntry update(LinkedProductEntry linkedProductEntry);

    public void remove (Integer linkedProductEntryId);
}
