package com.shopservice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.shopservice.assemblers.ProductAssembler;
import com.shopservice.dao.*;
import com.shopservice.domosed.ManufacturerRepository;

import javax.inject.Singleton;

public class MServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProductGroupRepository.class).to(HibernateProductGroupRepository.class).in(Singleton.class);
        bind(Group2ProductRepository.class).to(HibernateGroup2ProductRepository.class).in(Singleton.class);
        bind(ProductEntryRepository.class).to(HibernateProductEntryRepository.class).in(Singleton.class);
        bind(ProductProviderRepository.class).to(HibernateProductProviderRepository.class).in(Singleton.class);
        bind(LinkedProductEntryRepository.class).to(HibernateLinkedProductEntryRepository.class).in(Singleton.class);
        bind(ClientsCategoryRepository.class).to(HibernateClientsCategoryRepository.class).in(Singleton.class);

        bind(ClientSettingsRepository.class).to(HibernateClientSettingsRepository.class).in(Singleton.class);
        bind(ClientSettingsRepository.class).annotatedWith(Names.named(CachedClientSettingsRepository.BASE)).to(HibernateClientSettingsRepository.class).in(Singleton.class);

        bind(ProductAssembler.class).in(Singleton.class);

        /* domosed */
        bind(ManufacturerRepository.class).in(Singleton.class);
    }
}
