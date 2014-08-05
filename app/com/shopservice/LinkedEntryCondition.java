package com.shopservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 01.08.2014.
 */
public class LinkedEntryCondition {
    public Integer providerId;
    public Boolean linked;
    public List<String> clientProductsId = new ArrayList<>();
    public Integer offset;
    public Integer limit;

    public LinkedEntryCondition(Integer providerId) {
        this.providerId = providerId;
    }

    public LinkedEntryCondition(Integer providerId, Boolean linked, List<String> clientProductsId, Integer offset, Integer limit) {
        this.providerId = providerId;
        this.linked = linked;
        this.clientProductsId = clientProductsId;
        this.offset = offset;
        this.limit = limit;
    }

    public LinkedEntryCondition(Integer providerId, Boolean linked, Integer offset, Integer limit) {
        this.providerId = providerId;
        this.linked = linked;
        this.offset = offset;
        this.limit = limit;
    }
}
