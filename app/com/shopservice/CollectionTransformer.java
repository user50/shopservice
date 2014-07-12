package com.shopservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user50 on 22.06.2014.
 */
public class CollectionTransformer{

    public <T,B> List<T> transform( Collection<B> toTransform, Transformer<T,B> transformer)
    {
        List<T> result = new ArrayList<>();
        for (B b : toTransform) {
            result.add(transformer.transform(b));
        }

        return result;
    }

    public static interface Transformer<T,B>
    {
        public T transform(B b);
    }

}
