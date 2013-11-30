package com.shopservice.modules;

import com.google.inject.AbstractModule;
import com.shopservice.dao.EbeanGroup2ProductRepository;
import com.shopservice.dao.EbeanProductGroupRepository;
import com.shopservice.dao.Group2ProductRepository;
import com.shopservice.dao.ProductGroupRepository;

import javax.inject.Singleton;

public class MServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProductGroupRepository.class).to(EbeanProductGroupRepository.class).in(Singleton.class);
        bind(Group2ProductRepository.class).to(EbeanGroup2ProductRepository.class).in(Singleton.class);
    }
}
