package com.shopservice.dao;

import com.shopservice.Util;
import com.shopservice.domain.ProductGroup;
import com.shopservice.exception.Description;
import com.shopservice.exception.ValidationException;
import play.cache.Cache;
import play.mvc.Http;

/**
 * Created by user50 on 29.06.2014.
 */
public class ProductGroupValidator extends ProductGroupWrapper {
    protected ProductGroupValidator(ProductGroupRepository repository) {
        super(repository);
    }

    @Override
    public void save(ProductGroup productGroup) {


        super.save(productGroup);
    }
}
