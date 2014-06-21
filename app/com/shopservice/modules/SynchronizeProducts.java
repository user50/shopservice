package com.shopservice.modules;

import com.google.common.collect.Sets;
import com.shopservice.MServiceInjector;
import com.shopservice.Services;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.domain.Product;
import com.shopservice.domain.ProductEntry;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SynchronizeProducts implements MethodInterceptor{

    ProductEntryRepository productEntryRepository = MServiceInjector.injector.getInstance(ProductEntryRepository.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String clientId = (String)methodInvocation.getArguments()[0];
        //todo

        return methodInvocation.proceed();
    }

//    private List<Product> syncProducts(String clientId, String categoryId) throws Exception {
//        List<Product> products = Services.getProductDAO(clientId).getProducts( );
//        Set<ProductEntry> productEntriesFromClient = new HashSet<ProductEntry>();
//        for (Product product : products)
//            productEntriesFromClient.add(new ProductEntry(product));
//
//        Set<ProductEntry> productEntriesFromSettings = productEntryRepository.get(clientId);
//
//        productEntryRepository.delete(Sets.difference(productEntriesFromSettings, productEntriesFromClient));
//
//        productEntryRepository.add(clientId, Sets.difference(productEntriesFromClient, productEntriesFromSettings));
//
//        return products;
//    }
}
