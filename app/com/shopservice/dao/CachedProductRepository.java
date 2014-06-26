package com.shopservice.dao;

import com.shopservice.ProductConditions;
import com.shopservice.domain.Product;
import play.cache.Cache;
import play.mvc.Http;

import java.util.List;

public class CachedProductRepository extends ProductRepositoryWrapper {

    public CachedProductRepository(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public List<Product> find(final ProductConditions query) {
        Http.Cookie cookie = Http.Context.current().request().cookie("key");

        if (cookie == null)
            return super.find(query);

        CachedValue cached = (CachedValue) Cache.get("products:"+cookie.value());

        if (cached != null && cached.conditions.equals(query) )
        {
            if (query.offset != null && query.limit != null )
                return cached.products.subList(query.offset, Math.min(query.offset + query.limit, cached.products.size()) );
            else
                return cached.products;
        }

        ProductConditions conditionsWithoutPaging = new ProductConditions(query);
        conditionsWithoutPaging.offset = null;
        conditionsWithoutPaging.limit = null;
        List<Product> products = super.find(conditionsWithoutPaging);

        Cache.set("products:"+cookie.value(), new CachedValue(query, products), 60 * 5 /* 5 minutes */);

        if (query.offset != null && query.limit != null )
            return products.subList(query.offset, Math.min(query.offset + query.limit, products.size()));
        else
            return products;
    }

    @Override
    public int size(ProductConditions conditions) {
        Http.Cookie cookie = Http.Context.current().request().cookie("key");

        if (cookie == null)
            return super.size(conditions);

        CachedValue cached = (CachedValue) Cache.get("products:"+cookie.value());

        if (cached != null && cached.conditions.equals(conditions) )
            return cached.products.size();

        return super.size(conditions);
    }

    public static class CachedValue
    {
        public CachedValue(ProductConditions conditions, List<Product> products) {
            this.conditions = conditions;
            this.products = products;
        }

        public ProductConditions conditions;
        private List<Product> products;
    }
}
