package com.shopservice.dao;

import com.shopservice.domain.LinkedProductEntry;

import java.util.List;

/**
 * Created by user50 on 13.07.2014.
 */
public interface LinkedProductEntryRepository {

    public List<LinkedProductEntry> find(Integer providerId);

    public LinkedProductEntry create(LinkedProductEntry linkedProductEntry);

    public LinkedProductEntry update(LinkedProductEntry linkedProductEntry);

    public void remove (Integer linkedProductEntryId);
}
