package com.shopservice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.shopservice.modules.MServiceModule;

public class MServiceInjector {
    public static final Injector injector = Guice.createInjector(new MServiceModule());
}
