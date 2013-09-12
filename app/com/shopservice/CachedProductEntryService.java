package com.shopservice;

import com.shopservice.domain.ProductEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class CachedProductEntryService implements ProductEntryService {

    private Map<String, Collection<ProductEntry>> productEntries = new Hashtable<String, Collection<ProductEntry>>();

    private ProductEntryService wrappedService;

    public CachedProductEntryService(ProductEntryService wrappedService) {
        this.wrappedService = wrappedService;
    }

    @Override
    public Collection<ProductEntry> get(String clientId, final String categoryId) {
        return get(clientId, new Condition() {
            @Override
            public boolean isSuitable(ProductEntry entry) {
                return entry.categoryId.equals(categoryId);
            }
        });
    }

    @Override
    public Collection<ProductEntry> getSelected(String clientId) {
        return get(clientId, new Condition() {
            @Override
            public boolean isSuitable(ProductEntry entry) {
                return entry.checked;
            }
        });
    }

    @Override
    public Collection<ProductEntry> get(String clientId) {
        return get(clientId, new Condition() {
            @Override
            public boolean isSuitable(ProductEntry entry) {
                return true;
            }
        });
    }

    @Override
    public void setChecked(boolean checked, String productEntryId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setChecked(boolean checked, String clientId, String categoryId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Collection<ProductEntry> productEntry) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(Collection<ProductEntry> productEntry, String clientId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Collection<ProductEntry> get(String clientId, Condition condition)
    {
        if (!productEntries.containsKey(clientId))
            productEntries.put( clientId, wrappedService.get(clientId) );

        Collection<ProductEntry> entries = new ArrayList<ProductEntry>();
        for (ProductEntry entry : productEntries.get(clientId))
            if (condition.isSuitable(entry))
                entries.add(entry);

        return entries;
    }


    private static interface Condition
    {
        boolean isSuitable(ProductEntry entry);
    }
}
