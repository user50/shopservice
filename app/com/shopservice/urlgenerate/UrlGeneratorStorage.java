package com.shopservice.urlgenerate;


import java.util.HashMap;
import java.util.Map;

public class UrlGeneratorStorage {

    private Map<String,UrlGenerator> generators = new HashMap<String, UrlGenerator>();

    private final static UrlGeneratorStorage instance = new UrlGeneratorStorage();

    public static UrlGeneratorStorage getInstance()
    {
        return instance;
    }

    private UrlGeneratorStorage() {

        add(new Domosed());
        add(new MebelAlliance());
        add(new Demo());
        add(new Bilasad());
    }

    public UrlGenerator get(String clientId)
    {
        if (!generators.containsKey(clientId))
            return DefaultGenerator.getInstance();

        return generators.get(clientId);
    }

    private void add(UrlGenerator generator)
    {
        generators.put(generator.getClientId(), generator);
    }

}
