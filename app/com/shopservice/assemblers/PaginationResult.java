package com.shopservice.assemblers;

import java.util.Collection;

public class PaginationResult<T> {
    int totalCount;
    Collection<T> collectionResult;

    public PaginationResult(int totalCount, Collection<T> collectionResult) {
        this.totalCount = totalCount;
        this.collectionResult = collectionResult;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Collection<T> getCollectionResult() {
        return collectionResult;
    }

    public void setCollectionResult(Collection<T> collectionResult) {
        this.collectionResult = collectionResult;
    }
}
