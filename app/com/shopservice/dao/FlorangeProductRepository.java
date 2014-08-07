package com.shopservice.dao;

import com.shopservice.ProductConditions;
import com.shopservice.Util;
import com.shopservice.domain.Product;
import com.shopservice.productsources.Florange;
import com.shopservice.productsources.PersistenceByFile;
import com.shopservice.productsources.ProductSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 06.08.2014.
 */
public class FlorangeProductRepository implements ProductRepository {

    private static final String FILE_TO_STORE = "florange;products";

    private ProductSource source = new PersistenceByFile( new Florange(), FILE_TO_STORE);

    @Override
    public List<Product> find(ProductConditions conditions) {
        List<Product> found = new ArrayList<>();

        for (Product product : source.get(null))
            if ( checkConditions(product, conditions) )
                found.add(product);

        if (conditions.limit == null || conditions.offset == null)
            return found;

        return found.subList(conditions.offset, Math.min(found.size(), conditions.offset + conditions.limit));
    }

    private boolean checkConditions(Product product, ProductConditions conditions) {
        if (!conditions.productIds.isEmpty() && !conditions.productIds.contains(product.id)  )
            return false;

        if (conditions.categoryId != null && !product.category.id.equals(conditions.categoryId))
            return false;

        for (String word : conditions.words)
            if (!Util.matches(".*"+word+".*", product.name))
                return false;

        return true;
    }

    @Override
    public List<Product> find() {
        return find(new ProductConditions());
    }

    @Override
    public int size(ProductConditions conditions) {
        List<Product> found = new ArrayList<>();

        for (Product product : source.get(null))
            if ( checkConditions(product, conditions) )
                found.add(product);

        return found.size();
    }
}
