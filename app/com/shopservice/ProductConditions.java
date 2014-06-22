package com.shopservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by user50 on 21.06.2014.
 */
public class ProductConditions {
    public String categoryId;

    public Collection<String> productIds = new TreeSet<>();

    public Collection<String> words = new TreeSet<>();

    public Integer offset;

    public Integer limit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductConditions that = (ProductConditions) o;

        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null)
            return false;

        if (!productIds.equals(that.productIds))
            return false;

        if (!words.equals(that.words))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + productIds.hashCode();
        result = 31 * result + words.hashCode();
        return result;
    }
}
