package com.shopservice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.shopservice.assemblers.ProductAssembler;
import com.shopservice.dao.*;

import javax.inject.Singleton;

public class MServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProductGroupRepository.class).to(EbeanProductGroupRepository.class).in(Singleton.class);
        bind(Group2ProductRepository.class).to(EbeanGroup2ProductRepository.class).in(Singleton.class);
        bind(ProductEntryRepository.class).to(EbeanProductEntryRepository.class).in(Singleton.class);
        bind(ProductProviderRepository.class).to(EbeanProductProviderRepository.class).in(Singleton.class);
        bind(LinkedProductEntryRepository.class).to(EbeanLinkedProductEntryRepository.class).in(Singleton.class);
        bind(ClientsCategoryRepository.class).to(EbeanClientsCategoryRepository.class).in(Singleton.class);

        bind(ClientSettingsRepository.class).to(HibernateClientSettingsRepository.class).in(Singleton.class);
        bind(ClientSettingsRepository.class).annotatedWith(Names.named(CachedClientSettingsRepository.BASE)).to(HibernateClientSettingsRepository.class).in(Singleton.class);

        bind(ProductAssembler.class).in(Singleton.class);
    }
}
