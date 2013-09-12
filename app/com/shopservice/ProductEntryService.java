package com.shopservice;

import com.shopservice.domain.ProductEntry;

import java.util.Collection;
import java.util.List;

public interface ProductEntryService {

    Collection<ProductEntry> get(String clientId, String categoryId);

    Collection<ProductEntry> getSelected(String clientId);

    Collection<ProductEntry> get(String clientId);

    void setChecked(boolean checked, String productEntryId );

    void setChecked(boolean checked, String clientId, String categoryId );

    void delete(Collection<ProductEntry> productEntry);

    void add(Collection<ProductEntry> productEntry, String clientId);
}
